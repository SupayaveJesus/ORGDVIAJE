package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.orgdeviaje.data.model.Lugar
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme
import com.example.orgdeviaje.viewmodels.LugarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(
    viajeId: Int,
    viajeNombre: String,
    userName: String,
    navController: NavController,
    viewModel: LugarViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var ciudad by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var imagenUrl by remember { mutableStateOf(TextFieldValue("")) }
    var indicaciones by remember { mutableStateOf(TextFieldValue("")) }
    var tiempo by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }

    var showError by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Nuevo lugar para $viajeNombre") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del lugar") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("URL de la imagen") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = indicaciones,
                    onValueChange = { indicaciones = it },
                    label = { Text("Indicaciones (cómo llegar)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = tiempo,
                    onValueChange = { tiempo = it },
                    label = { Text("Tiempo estimado (ej. 2h)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio estimado") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (nombre.text.isNotEmpty() && ciudad.text.isNotEmpty()) {
                            val nuevoLugar = Lugar(
                                id = null,
                                nombre = nombre.text,
                                ciudad = ciudad.text,
                                descripcion = descripcion.text,
                                imagenUrl = imagenUrl.text,
                                indicaciones = indicaciones.text,
                                tiempoEstimado = tiempo.text,
                                precio = precio.text,
                                idViaje = viajeId
                            )

                            viewModel.createPlace(nuevoLugar) { success ->
                                if (success) {
                                    showSuccess = true
                                    navController.popBackStack()
                                } else {
                                    showError = true
                                }
                            }
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar lugar")
                }

                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Completa al menos nombre y ciudad.",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                if (showSuccess) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¡Lugar agregado con éxito!",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPlaceScreenPreview() {
    ORGDEViajeTheme {
        val navController = rememberNavController()
        AddPlaceScreen(
            viajeId = 1,
            viajeNombre = "Viaje a Disney",
            userName = "pepito",
            navController = navController
        )
    }
}
