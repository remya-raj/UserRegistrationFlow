package com.example.dailyrounds.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailyrounds.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun Navigation(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = Route.LOGIN) {
        composable(route = Route.LOGIN) {
            LoginSignUpScreen(
                countryList = userViewModel.readCountryDataFromAssets(LocalContext.current, "countries.json"),
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
        }
        composable(route = Route.LOGOUT) {
            LogoutScreen(navController = navController)
        }
    }
}

object Route {
    const val LOGIN = "LOGIN"
    const val LOGOUT = "LOGOUT"
}