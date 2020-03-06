package com.zatsepinvl.activity.play.team.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.fragment.dismissDialog
import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.databinding.DialogAddNewTeamBinding
import com.zatsepinvl.activity.play.team.adapter.ColorListAdapter
import com.zatsepinvl.activity.play.team.fragment.UpdateTeamDialogRequestCode.REQUEST_NEW
import com.zatsepinvl.activity.play.team.fragment.UpdateTeamDialogRequestCode.REQUEST_UPDATE
import com.zatsepinvl.activity.play.team.fragment.UpdateTeamDialogResultCode.RESULT_OK
import com.zatsepinvl.activity.play.team.model.*
import dagger.android.support.DaggerDialogFragment
import java.util.*
import javax.inject.Inject

enum class UpdateTeamDialogRequestCode(val code: Int) {
    REQUEST_NEW(0),
    REQUEST_UPDATE(1);

    companion object {
        fun valueOf(code: Int): UpdateTeamDialogRequestCode {
            return values().find { it.code == code }
                ?: throw IllegalArgumentException("Can not find code by value: $code")
        }
    }
}

enum class UpdateTeamDialogResultCode(val code: Int) {
    RESULT_OK(0),
}

class UpdateTeamDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var colorService: ColorService

    private val random: Random = Random()
    private lateinit var adapter: ColorListAdapter

    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val activity = requireActivity()
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

        val dialogContext = object : TeamDialogContext {
            override var nameInput: String
                get() = teamNameInput.text.toString()
                set(value) = teamNameInput.setText(value, TextView.BufferType.EDITABLE)

            override var colorInput: ColorId
                get() = colorService.getColorByIndex(
                    (binding.addNewTeamColorsList.adapter as ColorListAdapter).selectedIndex
                ).id
                set(value) {
                    val colorIndex = colors.indexOfFirst { it.id == value }
                    changeColor(colorIndex)
                    colorsList.smoothScrollToPosition(colorIndex)
                }

            override fun selectRandomInputs() {
                selectRandomColorAndName()
            }

            override val args: Bundle?
                get() = arguments
        }

        val requestCode: UpdateTeamDialogRequestCode =
            UpdateTeamDialogRequestCode.valueOf(targetRequestCode)
        val dialog = when (requestCode) {
            REQUEST_NEW -> NewTeamDialog(dialogContext)
            REQUEST_UPDATE -> UpdateTeamDialog(dialogContext)
        }
        binding.addNewTeamLuckyButton.setOnClickListener { selectRandomColorAndName() }

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .setPositiveButton(dialog.positiveButtonRes) { _, _ ->
                targetFragment?.onActivityResult(targetRequestCode, RESULT_OK.code, dialog.intent)
            }
            .setNegativeButton(R.string.cancel,
                dismissDialog
            )
            .setTitle(dialog.dialogTitleRes)
            .create()
    }

    private fun changeColor(index: Int) {
        adapter.selectedIndex = index
        adapter.notifyDataSetChanged()
    }
}


interface TeamDialogContext {
    var nameInput: String
    var colorInput: ColorId
    fun selectRandomInputs()
    val args: Bundle?
}

interface TeamDialog {
    val positiveButtonRes: Int
    val dialogTitleRes: Int
    val intent: Intent
}

private class NewTeamDialog(
    private val context: TeamDialogContext,
    override val positiveButtonRes: Int = R.string.save,
    override val dialogTitleRes: Int = R.string.edit_team
) : TeamDialog {

    init {
        context.selectRandomInputs()
    }

    override val intent: Intent
        get() = Intent().putNewTeamDto(
            NewTeamDto(context.nameInput, context.colorInput)
        )

}

private class UpdateTeamDialog(
    private val context: TeamDialogContext,
    override val positiveButtonRes: Int = R.string.save,
    override val dialogTitleRes: Int = R.string.edit_team
) : TeamDialog {

    private val teamId: String

    init {
        val editTeamDto = context.args!!.getUpdateTeamDto()
        teamId = editTeamDto.id

        context.colorInput = editTeamDto.colorId
        context.nameInput = editTeamDto.name
    }

    override val intent: Intent
        get() = Intent().putUpdateTeamDto(
            UpdateTeamDto(teamId, context.nameInput, context.colorInput)
        )
}

