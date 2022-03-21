package com.example.retrofit.addGroups

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.groups.AdapterGroup
import com.example.retrofit.groups.Group

class AdapterAddGroup(
    private var context: Context,
    private var nameUserList: ArrayList<UserList>,
    private var onClickListener: OnClickListener
    ) : RecyclerView.Adapter<AdapterAddGroup.ViewHolder>() {

    interface OnClickListener{
        fun onGroupClick(userList: UserList, position: Int)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserList = nameUserList[position]
        holder.userName.text = model.name
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                onClickListener.onGroupClick(model, position)
            }
//            if(!isChecked){
//
//        }
        }
    }

    override fun getItemCount(): Int {
        return nameUserList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.check_box_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.addNewUser)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}