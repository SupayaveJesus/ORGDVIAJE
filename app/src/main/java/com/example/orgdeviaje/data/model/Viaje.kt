package com.example.orgdeviaje.data.model

import com.google.gson.annotations.SerializedName

class Viaje (
    val id: Int?,

    @SerializedName("name")
    val nombre: String?,

    @SerializedName("username")
    val usuario: String?,

    @SerializedName("country")
    val pais: String?
)