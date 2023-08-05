package com.example.dailyrounds.repository

import com.example.dailyrounds.db.UserDatabase
import com.example.dailyrounds.models.User

class UserRepository(
    private val userDatabase: UserDatabase
) {

    suspend fun addUser(user: User) {
        userDatabase.userDao().addUser(user = user)
    }

    suspend fun getUsersWithMatchingUsername(username: String): List<User> {
        return userDatabase.userDao().getUsersWithUsername(username = username)
    }
}