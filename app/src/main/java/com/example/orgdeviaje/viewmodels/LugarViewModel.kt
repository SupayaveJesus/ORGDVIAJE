package com.example.orgdeviaje.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orgdeviaje.data.RetrofitInstance
import com.example.orgdeviaje.data.model.Lugar
import kotlinx.coroutines.launch

class LugarViewModel : ViewModel() {
    val listLugares = mutableStateListOf<Lugar>()

    fun getAllPlaces() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getLugares()
                listLugares.clear()
                listLugares.addAll(result)
            } catch (e: Exception) {
                println("Error al obtener lugares: ${e.message}")
            }
        }
    }

    suspend fun getPlaceById(id: Int): Lugar? {
        return try {
            RetrofitInstance.api.getLugarPorId(id)
        } catch (e: Exception) {
            println("Error al obtener lugar por ID: ${e.message}")
            null
        }
    }

    fun getPlacesByTrip(tripId: Int) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getLugaresPorViaje(tripId)
                listLugares.clear()
                listLugares.addAll(result)
            } catch (e: Exception) {
                println("Error al obtener lugares del viaje #$tripId: ${e.message}")
            }
        }
    }

    fun createPlace(lugar: Lugar, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val nuevo = RetrofitInstance.api.crearLugar(lugar)
                listLugares.add(nuevo)
                onResult(true)
            } catch (e: Exception) {
                println("Error al crear lugar: ${e.message}")
                onResult(false)
            }
        }
    }

    fun updatePlace(lugar: Lugar, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val actualizado = RetrofitInstance.api.editarLugar(lugar.id!!, lugar)
                val index = listLugares.indexOfFirst { it.id == lugar.id }
                if (index >= 0) listLugares[index] = actualizado
                onResult(true)
            } catch (e: Exception) {
                println("Error al actualizar lugar: ${e.message}")
                onResult(false)
            }
        }
    }

    fun deletePlace(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.eliminarLugar(id)
                listLugares.removeIf { it.id == id }
                onResult(true)
            } catch (e: Exception) {
                println("Error al eliminar lugar: ${e.message}")
                onResult(false)
            }
        }
    }
}
