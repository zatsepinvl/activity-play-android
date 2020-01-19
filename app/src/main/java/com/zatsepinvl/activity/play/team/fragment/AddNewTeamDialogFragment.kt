package com.zatsepinvl.activity.play.team.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.color.Color
import com.zatsepinvl.activity.play.color.ColorId
import com.zatsepinvl.activity.play.color.ColorService
import com.zatsepinvl.activity.play.databinding.DialogAddNewTeamBinding
import com.zatsepinvl.activity.play.databinding.ViewAddNewTeamColorItemBinding
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class AddNewTeamDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var colorService: ColorService

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        val inflater = activity.layoutInflater

        val binding = DialogAddNewTeamBinding.inflate(inflater)
        val colorsList = binding.addNewTeamColorsList
        val adapter = ColorListAdapter(colorService.getAllColors(), inflater)
        colorsList.adapter = adapter
        colorsList.choiceMode = ListView.CHOICE_MODE_SINGLE
        colorsList.setOnItemClickListener { _, _, position, _ ->
            adapter.selectedIndex = position
            adapter.notifyDataSetChanged()
        }

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .setPositiveButton("Create") { _, _ ->
                targetFragment?.onActivityResult(targetRequestCode, 0, Intent().apply {
                    putNewTeam(
                        NewTeam(
                            binding.addNewTeamNameTextInput.text.toString(),
                            colorService.getColorByIndex(adapter.selectedIndex).id
                        )
                    )
                })
            }
            .setNegativeButton("Cancel") { _, _ -> this.dismiss() }
            .setTitle("New team")
            .create()
    }

}

class ColorListAdapter(
    private val colors: List<Color>,
    private val inflater: LayoutInflater
) : BaseAdapter() {

    var selectedIndex = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val colorItemBinding = if (convertView == null) {
            val view = inflater.inflate(R.layout.view_add_new_team_color_item, null)
            val binding = ViewAddNewTeamColorItemBinding.bind(view)
            view.tag = binding
            binding
        } else {
            convertView.tag as ViewAddNewTeamColorItemBinding
        }
        colorItemBinding.color = getItem(position)
        colorItemBinding.selected = position == selectedIndex
        return colorItemBinding.root
    }

    override fun getItem(position: Int) = colors[position]


    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = colors.size
}

data class NewTeam(
    val name: String,
    val colorId: ColorId
)

fun Intent.getNewTeam(): NewTeam {
    return NewTeam(
        getStringExtra("name"),
        getSerializableExtra("colorId") as ColorId
    )
}

fun Intent.putNewTeam(newTeam: NewTeam) {
    putExtra("name", newTeam.name)
    putExtra("colorId", newTeam.colorId)
}