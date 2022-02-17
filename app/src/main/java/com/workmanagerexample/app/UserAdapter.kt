package com.workmanagerexample.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.workmanagerexample.app.databinding.UserBinding

class UserAdapter(
    var userList: List<MyUsers>,
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: UserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(userList[position]) {
                binding.textFirstName.text = this.first
                binding.textLastName.text = this.last
                binding.textBorn.text = this.born.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}