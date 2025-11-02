package com.example.recursosaprendizajeapp.network

import com.example.recursosaprendizajeapp.model.Recurso
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ResourceService {

    @GET("Recurso")
    suspend fun getAllResources(): Response<List<Recurso>>

    @GET("Recurso/{id}")
    suspend fun getResourceById(@Path("id") id: String): Response<Recurso>

    @POST("Recurso")
    suspend fun addResource(@Body recurso: Recurso): Response<Recurso>

    @PUT("Recurso/{id}")
    suspend fun updateResource(
        @Path("id") recursoId: String,
        @Body recursoActualizado: Recurso
    ): Response<Recurso>

    @DELETE("Recurso/{id}")
    suspend fun deleteResource(@Path("id") recursoId: String): Response<Unit>
}