package com.example.mapv2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mapv2.data.dataClasses.User
import com.example.mapv2.data.database.UserDao

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

}