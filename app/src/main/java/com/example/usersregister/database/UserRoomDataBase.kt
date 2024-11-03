package com.example.usersregister.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDataBase : RoomDatabase(){
    abstract fun userDao():UserDao
    companion object {
        @Volatile
        private var INSTANCE : UserRoomDataBase? = null
        fun getInstance(context: Context):UserRoomDataBase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDataBase::class.java,
                        "user_database"
                    ).build()
                }
                return instance
            }
        }
    }
}