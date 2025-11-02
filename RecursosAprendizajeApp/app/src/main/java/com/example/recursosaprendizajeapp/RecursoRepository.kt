package com.example.recursosaprendizajeapp

import android.util.Log
import com.example.recursosaprendizajeapp.model.Recurso
import com.example.recursosaprendizajeapp.network.ResourceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RecursoRepository(private val resourceService: ResourceService) {

    suspend fun fetchAllResources(): List<Recurso>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = resourceService.getAllResources()
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                Log.e("Repository", "Excepción de red al listar recursos", e)
                null
            }
        }
    }

    suspend fun createRecurso(recurso: Recurso): Recurso? {
        return withContext(Dispatchers.IO) {
            try {
                val response = resourceService.addResource(recurso)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                Log.e("Repository", "Excepción al crear recurso", e)
                null
            }
        }
    }

    suspend fun updateRecurso(id: String, recurso: Recurso): Recurso? {
        return withContext(Dispatchers.IO) {
            try {
                val response = resourceService.updateResource(id, recurso)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                Log.e("Repository", "Excepción al modificar recurso $id", e)
                null
            }
        }
    }

    suspend fun deleteRecurso(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = resourceService.deleteResource(id)
                if (response.isSuccessful) {
                    true
                } else {
                    Log.e("Repository", "Error al eliminar recurso $id: Código ${response.code()}")
                    false
                }
            } catch (e: Exception) {
                Log.e("Repository", "Excepción al eliminar recurso $id", e)
                false
            }
        }
    }

    suspend fun fetchResourceById(id: String): Recurso? {
        return withContext(Dispatchers.IO) {
            try {
                val response = resourceService.getResourceById(id)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                Log.e("Repository", "Excepción al buscar recurso por ID", e)
                null
            }
        }
    }
}