package com.example.usersregister

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.usersregister.database.User
import com.example.usersregister.databinding.ListItemBinding

class UserRecyclerViewAdapter(private val clickListener:(User)->Unit):RecyclerView.Adapter<UserViewHolder>() {

    private val userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setList(users:List<User>){
        userList.clear()
        userList.addAll(users)
    }
}

class UserViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(user: User, clickListener:(User)->Unit){
        binding.apply {
            tvName.text = user.name
            tvEmail.text = user.email
            root.setOnClickListener{
                clickListener(user)
            }
        }

    }
}