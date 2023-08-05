package com.example.dailyrounds.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyrounds.models.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUsersWithUsername(username: String): List<User>
}