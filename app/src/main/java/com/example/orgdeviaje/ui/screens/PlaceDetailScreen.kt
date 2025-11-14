package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.example.orgdeviaje.data.model.Lugar
import com.example.orgdeviaje.ui.nav.NavScreens
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme
import com.example.orgdeviaje.viewmodels.LugarViewModel
import com.example.orgdeviaje.viewmodels.ViajeViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    lugarId: Int,
    navController: NavController,
    username: String = "",
    viajeId: Int,
    viewModel: LugarViewModel = viewModel(),
    viajeViewModel: ViajeViewModel = viewModel()
) {
    var lugar by remember { mutableStateOf<Lugar?>(null) }
    var loading by remember { mutableStateOf(true) }
    var viajeAutor by remember { mutableStateOf("") }

    LaunchedEffect(lugarId) {
        loading = true
        try {
            // 🔹 Cargar el lugar directamente
            lugar = viewModel.getPlaceById(lugarId)

            // 🔹 Cargar los viajes
            viajeViewModel.getAllTrips()

            // 🔹 Esperar a que se carguen los viajes (evita null en viajeAutor)
            snapshotFlow { viajeViewModel.listViajes.size }
                .collectLatest { size ->
                    if (size > 0) {
                        val viaje = viajeViewModel.listViajes.find { it.id == viajeId }
                        viajeAutor = viaje?.usuario ?: ""
                        println("DEBUG: username=$username | viajeAutor=$viajeAutor") // 🧠 verificación
                        loading = false
                        this.cancel()
                    }
                }
        } catch (e: Exception) {
            println("Error cargando lugar: ${e.message}")
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Lugar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
                loading -> CircularProgressIndicator()
                lugar == null -> Text("No se encontró la información del lugar.")
                else -> LugarDetalleCard(lugar!!, username, viajeAutor, navController, viewModel)
            }
        }
    }
}

@Composable
fun LugarDetalleCard(
    lugar: Lugar,
    username: String,
    viajeAutor: String,
    navController: NavController,
    viewModel: LugarViewModel
) {
    val esPropietario = username == viajeAutor

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
            Text(lugar.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!lugar.indicaciones.isNullOrEmpty()) {
            Text("Cómo llegar: ${lugar.indicaciones}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!lugar.tiempoEstimado.isNullOrEmpty()) {
            Text(
                "Tiempo estimado: ${lugar.tiempoEstimado}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!lugar.precio.isNullOrEmpty()) {
            Text("Precio: ${lugar.precio}", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (esPropietario) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {
                        navController.navigate("${NavScreens.EDIT_PLACE.name}/${lugar.id}/${lugar.idViaje}/Editar ${lugar.nombre}/$username")
                    }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Editar")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    onClick = {
                        viewModel.deletePlace(lugar.id!!) { success ->
                            if (success) {
                                navController.popBackStack()
                            }
                        }
                    }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Eliminar")
                }
            }
        } else {
            Text(
                text = "Solo el creador puede editar o eliminar este lugar.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlaceDetailScreenPreview() {
    ORGDEViajeTheme {
        val navController = rememberNavController()
        PlaceDetailScreen(
            lugarId = 1,
            navController = navController,
            username = "pepito",
            viajeId = 1
        )
    }
}
