package com.example.usersregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersregister.database.User
import com.example.usersregister.database.UserDao
import kotlinx.coroutines.launch

class UserViewModel(private val dao: UserDao):ViewModel() {
    val users = dao.getAllUsers()

    fun insertUser(user: User) = viewModelScope.launch {
        dao.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        dao.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        dao.deleteUser(user)
    }
}