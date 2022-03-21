package com.example.retrofit.groups

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.retrofit.R





class AdapterGroup(
    private var context: Context,
    var courseModelArrayList: ArrayList<Group>,
    var onClickListener: OnClickListener
) : RecyclerView.Adapter<AdapterGroup.ViewHolder>() {

    interface OnClickListener{
        fun onGroupClick(group: Group,position: Int)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Group = courseModelArrayList[position]
        holder.groupName.text = model.name
        holder.itemView.setOnClickListener {
            onClickListener.onGroupClick(model, position)

        }

    }

    override fun getItemCount(): Int {
        return courseModelArrayList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupName: TextView = itemView.findViewById(R.id.groupName)
    }

}