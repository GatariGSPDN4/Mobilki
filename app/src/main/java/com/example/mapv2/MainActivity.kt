package com.example.mapv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.room.Room
import com.example.mapv2.data.database.UserDao
import com.example.mapv2.data.database.UserDatabase
import com.example.mapv2.data.network.RequestViewModel
import com.example.mapv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var userDao: UserDao
    private lateinit var binding: ActivityMainBinding
    val viewModel: RequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = binding.fragmentContainerView
        val db = Room.databaseBuilder(this, UserDatabase::class.java,"user").fallbackToDestructiveMigration().allowMainThreadQueries().build()
        userDao = db.userDao()
    }
}