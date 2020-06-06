package com.zatsepinvl.activityplay.team.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.color.Color
import com.zatsepinvl.activityplay.databinding.ViewAddNewTeamColorItemBinding

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