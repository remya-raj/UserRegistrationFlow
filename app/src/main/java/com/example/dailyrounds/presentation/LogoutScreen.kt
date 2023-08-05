package com.example.dailyrounds.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutScreen(navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Daily Rounds", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFC31822))
            )
        },
        content = {
            Column(modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC31822)
                    ),
                    onClick = {
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_LONG).show()
                        navController.navigate(Route.LOGIN) {
                        navController.popBackStack()
                    }
                }) {
                    Text(text = "LOGOUT")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LogoutScreenPreview() {
    LogoutScreen(navController = rememberNavController())
}