package com.example.recursosaprendizajeapp.viewmodel

import androidx.lifecycle.*
import com.example.recursosaprendizajeapp.RecursoRepository
import com.example.recursosaprendizajeapp.model.Recurso
import com.example.recursosaprendizajeapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecursoViewModel : ViewModel() {

    private val repository = RecursoRepository(RetrofitClient.resourceService)

    private val _recursosList = MutableLiveData<List<Recurso>>()
    val recursosList: LiveData<List<Recurso>> = _recursosList

    private val _recursoCreado = MutableLiveData<Recurso?>()
    val recursoCreado: LiveData<Recurso?> = _recursoCreado

    fun fetchResources() {
        viewModelScope.launch {
            val result = repository.fetchAllResources()
            _recursosList.value = result ?: emptyList()
        }
    }

    fun saveResource(recurso: Recurso) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val resultRecurso = if (recurso.id != null) {
                repository.updateRecurso(recurso.id!!, recurso)
            } else {
                repository.createRecurso(recurso)
            }
            _recursoCreado.postValue(resultRecurso)
        } catch (e: Exception) {
            _recursoCreado.postValue(null)
        }
    }

    fun deleteRecurso(id: String) {
        viewModelScope.launch {
            val success = repository.deleteRecurso(id)

            if (success) {
                fetchResources()
            }
        }
    }

    fun searchResourceById(id: String) {
        viewModelScope.launch {
            if (id.isBlank()) {
                _recursosList.value = emptyList()
                return@launch
            }

            val result = repository.fetchResourceById(id)

            if (result != null) {
                _recursosList.value = listOf(result)
            } else {
                _recursosList.value = emptyList()
            }
        }
    }

}