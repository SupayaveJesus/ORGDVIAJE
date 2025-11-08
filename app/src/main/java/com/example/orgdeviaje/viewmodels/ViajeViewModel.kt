package com.example.orgdeviaje.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orgdeviaje.data.RetrofitInstance
import com.example.orgdeviaje.data.model.Viaje
import kotlinx.coroutines.launch

class ViajeViewModel : ViewModel() {
    val listViajes = mutableStateListOf<Viaje>()

    fun getAllTrips() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getViajes()
                listViajes.clear()
                listViajes.addAll(result)
                println("Viajes cargados: ${result.size}")
            } catch (e: Exception) {
                println("Error al obtener viajes: ${e.message}")
            }
        }
    }

    fun getTripsByUsername(username: String) {
        viewModelScope.launch {
            try {
                val res = RetrofitInstance.api.getViajesPorUsuario(username)
                listViajes.clear()
                listViajes.addAll(res)
                println("Viajes de $username: ${res.size}")
            } catch (e: Exception) {
                println("Error al obtener viajes por usuario: ${e.message}")
            }
        }
    }

    fun createTrip(viaje: Viaje, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val nuevo = RetrofitInstance.api.crearViaje(viaje)
                listViajes.add(nuevo)
                onResult(true)
            } catch (e: Exception) {
                println("Error al crear viaje: ${e.message}")
                onResult(false)
            }
        }
    }

    fun updateTrip(id: Int, viaje: Viaje, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val actualizado = RetrofitInstance.api.editarViaje(id, viaje)
                val index = listViajes.indexOfFirst { it.id == id }
                if (index >= 0) listViajes[index] = actualizado
                onResult(true)
            } catch (e: Exception) {
                println("Error al actualizar viaje: ${e.message}")
                onResult(false)
            }
        }
    }

    fun deleteTrip(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.eliminarViaje(id)
                listViajes.removeIf { it.id == id }
                onResult(true)
            } catch (e: Exception) {
                println("Error al eliminar viaje: ${e.message}")
                onResult(false)
            }
        }
    }
}
