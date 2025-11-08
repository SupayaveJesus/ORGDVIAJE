package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SplashLoginScreen(
    navController: NavController
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Viajes App",
                fontSize = 26.sp,
                style = MaterialTheme . typography . headlineSmall

            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Ingrese su usuario:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                singleLine = true,
                placeholder = { Text("Ej: pepito") }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (username.text.isNotEmpty()) {
                        navController.navigate("home/${username.text}")
                    }
                }
            ) {
                Text("Ingresar")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SplashLoginScreenPreview() {
    SplashLoginScreen(navController = rememberNavController())
}
