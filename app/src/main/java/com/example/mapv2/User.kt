package com.example.mapv2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "mail") var mail: String?,
    @ColumnInfo(name = "password") var password: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
