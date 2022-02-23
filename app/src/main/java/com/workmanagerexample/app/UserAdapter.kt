package com.workmanagerexample.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.workmanagerexample.app.databinding.UserBinding

class UserAdapter(var listUser: MutableList<MyUsers>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: MyUsers = listUser[position]
        holder.binding.textFirstName.text = user.first
        holder.binding.textLastName.text = user.last
        holder.binding.textBorn.text = user.born.toString()
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun addData(list: MutableList<MyUsers>) {
        listUser.clear()
        listUser.addAll(list)
        notifyDataSetChanged()
    }

}