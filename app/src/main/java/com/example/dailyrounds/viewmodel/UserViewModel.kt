package com.example.dailyrounds.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyrounds.models.CountryData
import com.example.dailyrounds.models.CountryInfo
import com.example.dailyrounds.models.User
import com.example.dailyrounds.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun registerUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user = user)
        }
    }

    suspend fun verifyValidUsername(username: String): Deferred<Boolean> {
        return coroutineScope {
            async { userRepository.getUsersWithMatchingUsername(username = username).isEmpty() }
        }
    }

    suspend fun verifyUsernameExists(username: String): Deferred<Boolean> {
        return coroutineScope {
            async { userRepository.getUsersWithMatchingUsername(username = username).isNotEmpty() }
        }
    }

    suspend fun getPasswordForUsername(username: String): Deferred<String> {
        return coroutineScope {
            async {
                val userList = userRepository.getUsersWithMatchingUsername(username = username)
                if (userList.isNotEmpty()) {
                    userList[0].password
                } else {
                    ""
                }
            }
        }
    }

    fun readCountryDataFromAssets(context: Context, fileName: String): List<String>? {
        val json: String
        try {
            val inputStream = context.assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        val countryData = Gson().fromJson(json, CountryData::class.java)
        val countryList = mutableListOf<String>()

        if (countryData != null) {
            val countries: Map<String, CountryInfo> = countryData.data
            for ((countryCode, countryInfo) in countries) {
                countryList.add(countryInfo.country)
            }
        }

        return countryList
    }
}