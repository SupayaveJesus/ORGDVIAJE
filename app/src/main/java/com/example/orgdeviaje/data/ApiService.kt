package com.example.orgdeviaje.data

import com.example.orgdeviaje.data.model.Viaje
import com.example.orgdeviaje.data.model.Lugar
import retrofit2.http.*

interface ApiService {

    // VIAJE
    @GET("trips")
    suspend fun getViajes(): List<Viaje>

    @GET("trips/{usuario}")
    suspend fun getViajesPorUsuario(@Path("usuario") usuario: String): List<Viaje>

    @POST("trips")
    suspend fun crearViaje(@Body viaje: Viaje): Viaje

    @PUT("trips/{id}")
    suspend fun editarViaje(@Path("id") id: Int, @Body viaje: Viaje): Viaje

    @DELETE("trips/{id}")
    suspend fun eliminarViaje(@Path("id") id: Int)

    // LUGAR
    @GET("trips/{viajeId}/places")
    suspend fun getLugaresPorViaje(@Path("viajeId") viajeId: Int): List<Lugar>

    @GET("places")
    suspend fun getLugares(): List<Lugar>

    @GET("places/{id}")
    suspend fun getLugarPorId(@Path("id") id: Int): Lugar

    @POST("places")
    suspend fun crearLugar(@Body lugar: Lugar): Lugar

    @PUT("places/{id}")
    suspend fun editarLugar(@Path("id") id: Int, @Body lugar: Lugar): Lugar

    @DELETE("places/{id}")
    suspend fun eliminarLugar(@Path("id") id: Int)

}
