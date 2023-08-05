package com.example.dailyrounds.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyrounds.R
import com.example.dailyrounds.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSignUpScreen(
    countryList: List<String>?,
    verifyValidUsername: (String) -> Boolean,
    verifyUsernameExists: (String) -> Boolean,
    getPasswordForUsername: (String) -> String,
    onSignUp: (User) -> Unit,
    onLogin: () -> Unit
) {

    val context = LocalContext.current
    val selectCountry = stringResource(id = R.string.select_country)
    var isSignUpState by remember { mutableStateOf(false) }
    var userNameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(selectCountry) }
    var expanded by remember { mutableStateOf(false) }

    fun loginSetUp() {
        isSignUpState = false
        userNameInput = ""
        passwordInput = ""
    }

    fun signUpSetUp() {
        isSignUpState = true
        userNameInput = ""
        passwordInput = ""
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Header()

        TextField(modifier = Modifier
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(30.dp))
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.9f),
            value = userNameInput,
            placeholder = { Text(text = stringResource(id = R.string.username), fontSize = 14.sp) },
            onValueChange = {
                userNameInput = it
            })

        TextField(modifier = Modifier
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.9f),
            value = passwordInput,
            placeholder = { Text(text = stringResource(id = R.string.password), fontSize = 14.sp) },
            onValueChange = {
                passwordInput = it
            })

        if (isSignUpState) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.9f),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(selectedCountry)
                Icon(
                    modifier = Modifier.padding(vertical = 7.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "arrow"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                countryList?.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCountry = item
                            expanded = false
                        },
                        text = {
                            Text(text = item)
                        }
                    )
                }
            }
        }

        if (!isSignUpState) {
            Button(modifier = Modifier
                .padding(top = 15.dp)
                .clip(RoundedCornerShape(30.dp))
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC31822)
                ),
                onClick = {
                    if (isSignUpState) {
                        loginSetUp()
                    } else {
                        handleLoginErrorMessage(
                            context = context,
                            username = userNameInput,
                            password = passwordInput,
                            verifyUsernameExists = verifyUsernameExists,
                            getPasswordForUsername = getPasswordForUsername,
                            onLogin = onLogin
                        )
                    }
                }) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 7.dp),
                    text = stringResource(id = R.string.login),
                    fontSize = 17.sp
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f)
                    .clickable {
                        isSignUpState = true
                    },
                text = stringResource(id = R.string.need_an_account),
                textAlign = TextAlign.Center
            )
        }

        if (isSignUpState) {
            Button(modifier = Modifier
                .padding(top = 15.dp)
                .clip(RoundedCornerShape(30.dp))
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC31822)
                ),
                onClick = {
                    if (isSignUpState) {
                        handleSignUpErrorMessage(
                            context = context,
                            username = userNameInput,
                            password = passwordInput,
                            country = selectCountry,
                            verifyValidUsername = verifyValidUsername,
                            onSignUp = onSignUp
                        )
                    } else {
                        signUpSetUp()
                    }
                }) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 7.dp),
                    text = stringResource(id = R.string.sign_up),
                    fontSize = 17.sp
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f)
                    .clickable {
                        isSignUpState = false
                    },
                text = stringResource(id = R.string.already_a_member),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Header() {
    Image(modifier = Modifier
        .padding(start = 30.dp, top = 40.dp)
        .size(140.dp)
        .clip(RoundedCornerShape(30.dp)),
        painter = painterResource(id = R.drawable.ic_daily_rounds_logo),
        contentDescription = "logo")

    Text(
        modifier = Modifier
            .padding(start = 30.dp, top = 15.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp,
        text = stringResource(id = R.string.login_or_sign_up),
    )
}

fun validatePassword(password: String): Boolean {
    val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&()]).{8,}$")
    return regex.matches(password)
}

fun handleLoginErrorMessage(
    context: Context,
    username: String,
    password: String,
    verifyUsernameExists: (String) -> Boolean,
    getPasswordForUsername: (String) -> String,
    onLogin: () -> Unit
) {
    if (!verifyUsernameExists(username)) {
        Toast.makeText(context, "username does not exists, please sign up", Toast.LENGTH_LONG).show()
    } else {
        val passwordForUsername = getPasswordForUsername(username)
        when {
            passwordForUsername.isEmpty() -> {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_LONG).show()
            }
            passwordForUsername != password -> {
                Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                onLogin()
            }
        }
    }
}

fun handleSignUpErrorMessage(
    context: Context,
    username: String,
    password: String,
    country: String,
    verifyValidUsername: (String) -> Boolean,
    onSignUp: (User) -> Unit,
    ) {
    when {
        username.isEmpty() || password.isEmpty() || country == "Select a country" -> {
            Toast.makeText(context, "please enter username and password and select country to proceed", Toast.LENGTH_LONG).show()
        }
        !validatePassword(password = password ) -> {
            Toast.makeText(context, "password should have min 8 characters with atleast one number, special characters, one lowercase letter, and one uppercase letter", Toast.LENGTH_LONG).show()
        }
        verifyValidUsername(username) -> {
            val user = User(username = username, password = password, country = country)
            onSignUp(user)
            Toast.makeText(context, "Sign up successful", Toast.LENGTH_LONG).show()
        }
        else -> {
            Toast.makeText(context, "username already taken, please try different username", Toast.LENGTH_LONG).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginSignUpScreenPreview() {
    LoginSignUpScreen(
        countryList = listOf(),
        verifyValidUsername = { true },
        verifyUsernameExists = { true },
        getPasswordForUsername = { "" },
        onSignUp = {},
        onLogin = {}
    )
}