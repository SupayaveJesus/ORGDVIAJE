package com.example.orgdeviaje.data.model

import com.google.gson.annotations.SerializedName

data class Lugar (
    val id: Int?,
    @SerializedName("name")
    val nombre: String?,

    @SerializedName("city")
    val ciudad: String?,

    @SerializedName("description")
    val descripcion: String?,

    @SerializedName("image_url")
    val imagenUrl: String?,

    @SerializedName("directions")
    val indicaciones: String?,

    @SerializedName("time_to_spend")
    val tiempoEstimado: String?,

    @SerializedName("price")
    val precio: String?,

    @SerializedName("trip_id")
    val idViaje: Int?
)