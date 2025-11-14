package com.example.orgdeviaje

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.orgdeviaje.ui.nav.NavScreens
import com.example.orgdeviaje.ui.screens.*
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ORGDEViajeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreens.SPLASH.name
    ) {
        // Splash / Login
        composable(NavScreens.SPLASH.name) {
            SplashLoginScreen(navController = navController)
        }

        // Home (todos los viajes)
        composable(
            route = "${NavScreens.HOME.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: ""
            HomeScreen(navController = navController, username = username)
        }

        //Mis viajes
        composable(
            route = "${NavScreens.MY_TRIPS.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: ""
            MisViajesScreen(username = username, navController = navController)
        }

        //Crear viaje
        composable(
            route = "${NavScreens.ADD_TRIP.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: ""
            AddTripScreen(username = username, navController = navController)
        }

        //Detalle del viaje
        composable(
            route = "${NavScreens.TRIP_DETAIL.name}/{viajeId}/{viajeNombre}/{username}",
            arguments = listOf(
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStack ->
            val viajeId = backStack.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStack.arguments?.getString("viajeNombre") ?: ""
            val username = backStack.arguments?.getString("username") ?: ""
            TripDetalleScreen(
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                username = username,
                navController = navController
            )
        }

        // Agregar lugar
        composable(
            route = "${NavScreens.ADD_PLACE.name}/{viajeId}/{viajeNombre}/{username}",
            arguments = listOf(
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStack ->
            val viajeId = backStack.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStack.arguments?.getString("viajeNombre") ?: ""
            val username = backStack.arguments?.getString("username") ?: ""
            AddPlaceScreen(
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                username = username,
                navController = navController
            )
        }
        // 🟣 Detalle del lugar
        composable(
            route = "${NavScreens.PLACE_DETAIL.name}/{lugarId}/{username}/{viajeId}",
            arguments = listOf(
                navArgument("lugarId") { type = NavType.IntType },
                navArgument("username") { type = NavType.StringType },
                navArgument("viajeId") { type = NavType.IntType }
            )
        ) { backStack ->
            PlaceDetailScreen(
                lugarId = backStack.arguments?.getInt("lugarId") ?: 0,
                username = backStack.arguments?.getString("username") ?: "",
                viajeId = backStack.arguments?.getInt("viajeId") ?: 0,
                navController = navController
            )
        }


        // 🟠 Editar lugar
        composable(
            route = "${NavScreens.EDIT_PLACE.name}/{lugarId}/{viajeId}/{viajeNombre}/{username}",
            arguments = listOf(
                navArgument("lugarId") { type = NavType.IntType },
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStack ->
            val lugarId = backStack.arguments?.getInt("lugarId") ?: 0
            val viajeId = backStack.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStack.arguments?.getString("viajeNombre") ?: ""
            val username = backStack.arguments?.getString("username") ?: ""

            EditPlaceScreen(
                lugarId = lugarId,
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                username = username,
                navController = navController
            )
        }


    }
}
