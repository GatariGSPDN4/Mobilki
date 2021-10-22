package com.example.mapv2

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll():List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT EXISTS (SELECT 1 FROM user WHERE mail LIKE :mail)")
    fun isExistByMail(mail: String) : Boolean

    @Query("SELECT * FROM user WHERE mail LIKE :mail LIMIT 1")
    fun findByMail(mail: String) : User

    @Insert
    fun insert(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}