package com.example.dailyrounds

import android.app.Application
import com.example.dailyrounds.db.UserDatabase
import com.example.dailyrounds.repository.UserRepository

class UserApplication : Application() {

    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val userDatabase = UserDatabase.getDatabase(applicationContext)
        userRepository = UserRepository(userDatabase)
    }
}