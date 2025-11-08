package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.orgdeviaje.data.RetrofitInstance
import com.example.orgdeviaje.data.model.Lugar
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme
import com.example.orgdeviaje.viewmodels.LugarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    lugarId: Int,
    navController: NavController,
    viewModel: LugarViewModel = viewModel()
) {
    var lugar by remember { mutableStateOf<Lugar?>(null) }
    var loading by remember { mutableStateOf(true) }

    // 🔹 Cargar los datos del lugar desde API directamente
    LaunchedEffect(lugarId) {
        try {
            val result = RetrofitInstance.api.getLugares()
            val encontrado = result.find { it.id == lugarId }
            lugar = encontrado
        } catch (e: Exception) {
            println("Error al cargar lugar: ${e.message}")
        } finally {
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Lugar") },
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
            contentAlignment = Alignment.Center
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }

                lugar == null -> {
                    Text(
                        text = "No se encontró la información del lugar.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LugarDetalleCard(lugar!!)
                }
            }
        }
    }
}

@Composable
fun LugarDetalleCard(lugar: Lugar) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!lugar.imagenUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(lugar.imagenUrl),
                contentDescription = lugar.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = lugar.nombre ?: "(Sin nombre)",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = lugar.ciudad ?: "(Sin ciudad)",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!lugar.descripcion.isNullOrEmpty()) {
            Text(
                text = lugar.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!lugar.indicaciones.isNullOrEmpty()) {
            Text(
                text = "📍 Cómo llegar: ${lugar.indicaciones}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!lugar.tiempoEstimado.isNullOrEmpty()) {
            Text(
                text = "🕒 Tiempo estimado: ${lugar.tiempoEstimado}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!lugar.precio.isNullOrEmpty()) {
            Text(
                text = "💰 Precio: ${lugar.precio}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlaceDetailPreview() {
    ORGDEViajeTheme {
        val navController = rememberNavController()
        PlaceDetailScreen(
            lugarId = 1,
            navController = navController
        )
    }
}
