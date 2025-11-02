package com.example.recursosaprendizajeapp.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recurso(

    @SerializedName("id")
    val id: String? =null,

    @SerializedName("Titulo")
    val titulo: String,

    @SerializedName("Descripcion")
    val descripcion: String,

    @SerializedName("Tipo")
    val tipo: String,

    @SerializedName("Enlace")
    val enlace: String,

    @SerializedName("Imagen")
    val imagen: String
) : Parcelable