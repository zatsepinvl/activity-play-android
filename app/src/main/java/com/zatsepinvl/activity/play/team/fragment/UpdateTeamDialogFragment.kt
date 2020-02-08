package com.zatsepinvl.activity.play.team.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.dismissDialog
import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.databinding.DialogAddNewTeamBinding
import com.zatsepinvl.activity.play.team.adapter.ColorListAdapter
import com.zatsepinvl.activity.play.team.fragment.UpdateTeamDialogCode.REQUEST_UPDATE
import com.zatsepinvl.activity.play.team.fragment.UpdateTeamDialogCode.RESULT_OK
import com.zatsepinvl.activity.play.team.model.*
import dagger.android.support.DaggerDialogFragment
import java.util.*
import javax.inject.Inject

enum class UpdateTeamDialogCode(
    val code: Int
) {
    RESULT_OK(0),
    REQUEST_NEW(1),
    REQUEST_UPDATE(2);

    companion object {
        fun valueOf(code: Int): UpdateTeamDialogCode {
            return values().find { it.code == code }
                ?: throw IllegalArgumentException("Can not find code by value: $code")
        }
    }
}

class UpdateTeamDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var colorService: ColorService

    private val random: Random = Random()
    private lateinit var adapter: ColorListAdapter

    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val activity = requireActivity()
        val code: UpdateTeamDialogCode = UpdateTeamDialogCode.valueOf(targetRequestCode)
        val inflater = activity.layoutInflater
        val binding = DialogAddNewTeamBinding.inflate(inflater)

        val teamNameInput = binding.addNewTeamNameTextInput
        val colorsList = binding.addNewTeamColorsList
        val colors = colorService.getAllColors()
        adapter = ColorListAdapter(colors, inflater)
        colorsList.adapter = adapter
        colorsList.choiceMode = ListView.CHOICE_MODE_SINGLE
        colorsList.setOnItemClickListener { _, _, position, _ -> changeColor(position) }

        val selectRandomColorAndName = {
            val names = requireContext().resources.obtainTypedArray(R.array.capitals)
            val name = names.getText(random.nextInt(names.length()))
            teamNameInput.setText(name, TextView.BufferType.EDITABLE)
            names.recycle()

            val colorIndex = random.nextInt(colors.size)
            changeColor(colorIndex)
            colorsList.smoothScrollToPosition(colorIndex)
        }
        binding.addNewTeamLuckyButton.setOnClickListener { selectRandomColorAndName() }

        var teamId: String? = null
        if (arguments != null && code == REQUEST_UPDATE) {
            val editTeamDto = arguments!!.getUpdateTeamDto()
            teamId = editTeamDto.id
            teamNameInput.setText(editTeamDto.name, TextView.BufferType.EDITABLE)

            val colorIndex = colors.indexOfFirst { it.id == editTeamDto.colorId }
            changeColor(colorIndex)
            colorsList.smoothScrollToPosition(colorIndex)
        } else {
            selectRandomColorAndName()
        }

        val selectedColorId = {
            colorService.getColorByIndex(
                (binding.addNewTeamColorsList.adapter as ColorListAdapter).selectedIndex
            ).id
        }
        val getIntent = {
            Intent().apply {
                if (code == REQUEST_UPDATE) {
                    putUpdateTeamDto(
                        UpdateTeamDto(teamId!!, teamNameInput.text.toString(), selectedColorId())
                    )
                } else {
                    putNewTeamDto(
                        NewTeamDto(teamNameInput.text.toString(), selectedColorId())
                    )
                }
            }
        }
        val positiveButtonRes = if (code == REQUEST_UPDATE) R.string.save else R.string.add
        val dialogTitleRes = if (code == REQUEST_UPDATE) R.string.editTeam else R.string.addTeam
        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .setPositiveButton(positiveButtonRes) { _, _ ->
                targetFragment?.onActivityResult(targetRequestCode, RESULT_OK.code, getIntent())
            }
            .setNegativeButton(R.string.cancel, dismissDialog)
            .setTitle(dialogTitleRes)
            .create()
    }

    private fun changeColor(index: Int) {
        adapter.selectedIndex = index
        adapter.notifyDataSetChanged()
    }
}
