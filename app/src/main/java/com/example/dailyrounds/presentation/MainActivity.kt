package com.example.dailyrounds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.dailyrounds.UserApplication
import com.example.dailyrounds.ui.theme.DailyRoundsTheme
import com.example.dailyrounds.viewmodel.UserViewModel
import com.example.dailyrounds.viewmodel.UserViewModelFactory
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = (application as UserApplication).userRepository
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]

        setContent {
            DailyRoundsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    LoginSignUpScreen(
                        countryList = userViewModel.readCountryDataFromAssets(this, "countries.json"),
                        verifyValidUsername = { username ->
                            runBlocking {
                                val deferredResult = userViewModel.verifyValidUsername(username)
                                val result = deferredResult.await()
                                result
                            }
                        },
                        verifyUsernameExists = { username ->
                            runBlocking {
                                val deferredResult = userViewModel.verifyUsernameExists(username)
                                val result = deferredResult.await()
                                result
                            }
                        },
                        getPasswordForUsername = { username ->
                            runBlocking {
                                val deferredResult = userViewModel.getPasswordForUsername(username)
                                val result = deferredResult.await()
                                result
                            }
                        },
                        onSignUp = { user ->
                            userViewModel.registerUser(user)
                            navController.navigate(Route.LOGOUT) {
                                navController.popBackStack()
                            }
                        },
                        onLogin = {
                            navController.navigate(Route.LOGOUT) {
                                navController.popBackStack()
                            }
                        }
                    )
                    Navigation(navController = navController, userViewModel = userViewModel)
                }
            }
        }
    }
}