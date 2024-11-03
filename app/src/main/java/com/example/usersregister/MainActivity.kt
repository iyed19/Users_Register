package com.example.usersregister

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersregister.database.User
import com.example.usersregister.database.UserRoomDataBase
import com.example.usersregister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserRecyclerViewAdapter
    private var isListItemClicked = false

    private lateinit var selectedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val dao = UserRoomDataBase.getInstance(application).userDao()
        val factory = UserViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        binding.apply {
            btnSave.setOnClickListener {
                if (isListItemClicked) {
                    updateUserData()
                    clearInputFields()
                } else {
                    saveUserData()
                    clearInputFields()
                }
            }

            btnClear.setOnClickListener {
                if (isListItemClicked) {
                    deleteUserData()
                    clearInputFields()
                    Toast.makeText(
                        this@MainActivity,
                        "The user ${selectedUser.name} is deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    clearInputFields()
                }
            }
        }

        initRecyclerView()

    }

    private fun saveUserData(){
        binding.apply {
            viewModel.insertUser(
                User(
                    0,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            Toast.makeText(
                this@MainActivity,
                "User saved successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clearInputFields(){
        binding.apply {
            etName.setText("")
            etEmail.setText("")
        }
    }

    private fun updateUserData(){
        binding.apply {
            viewModel.updateUser(
                User(
                    selectedUser.id,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            btnSave.text = "Save"
            btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun deleteUserData(){
        binding.apply {
            viewModel.deleteUser(
                User(
                    selectedUser.id,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            btnSave.text = "Save"
            btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun initRecyclerView(){
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = UserRecyclerViewAdapter {
            selectedItem:User -> listItemClicked(selectedItem)
        }
        binding.rvUser.adapter = adapter

        displayUsersList()
    }

    private fun displayUsersList(){
        viewModel.users.observe(this, {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(user: User){
        binding.apply {
            if (isListItemClicked && user.id == selectedUser.id) {
                // Deselect the item if it's clicked again
                clearInputFields()
                btnSave.text = "Save"
                btnClear.text = "Clear"
                isListItemClicked = false
            } else {
                // Select a new item
                selectedUser = user
                btnSave.text = "Update"
                btnClear.text = "Delete"
                isListItemClicked = true
                etName.setText(selectedUser.name)
                etEmail.setText(selectedUser.email)
            }
        }
    }

}