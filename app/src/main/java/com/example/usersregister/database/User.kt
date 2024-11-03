package com.example.usersregister.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("user_id")
    var id:Int,

    @ColumnInfo("user_name")
    var name:String,

    @ColumnInfo("user_email")
    var email:String

)
