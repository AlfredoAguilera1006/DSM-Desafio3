package com.example.recursosaprendizajeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.recursosaprendizajeapp.databinding.ActivityAddResourceBinding
import com.example.recursosaprendizajeapp.model.Recurso
import com.example.recursosaprendizajeapp.viewmodel.RecursoViewModel

class AddResourceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddResourceBinding
    private val recursoViewModel: RecursoViewModel by viewModels()
    private val EXTRA_RECURSO = "extra_recurso"
    private var existingResourceId: String? = null
    private var originalRecurso: Recurso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddResourceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalRecurso = intent.getParcelableExtra<Recurso>(EXTRA_RECURSO)

        if (originalRecurso != null) {
            existingResourceId = originalRecurso!!.id
            binding.etTitulo.setText(originalRecurso!!.titulo)
            binding.etDescripcion.setText(originalRecurso!!.descripcion)
            binding.etTipo.setText(originalRecurso!!.tipo)
            binding.etEnlace.setText(originalRecurso!!.enlace)
            binding.etImagenUrl.setText(originalRecurso!!.imagen)
        }

        recursoViewModel.recursoCreado.observe(this, Observer { recurso ->

            val message = if (existingResourceId != null) {
                "Recurso modificado con éxito, Siuuuu"
            } else {
                "Recurso guardado con éxito, Eso papú"
            }

            if (recurso != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar/modificar ¯|_(ツ)_|¯", Toast.LENGTH_LONG).show()
            }
        })

        binding.btnSaveResource.setOnClickListener {
            saveResource()
        }
    }

    private fun saveResource() {
        val titulo = binding.etTitulo.text.toString()
        val descripcion = binding.etDescripcion.text.toString()
        val tipo = binding.etTipo.text.toString()
        val enlace = binding.etEnlace.text.toString()
        val imagenUrl = binding.etImagenUrl.text.toString()

        if (titulo.isBlank() || descripcion.isBlank() || tipo.isBlank()) {
            Toast.makeText(this, "Por favor, completar los campos obligatorios.", Toast.LENGTH_SHORT).show()
            return
        }

        val resourceToSave = Recurso(
            id = existingResourceId,
            titulo = titulo,
            descripcion = descripcion,
            tipo = tipo,
            enlace = enlace,
            imagen = imagenUrl
        )

        recursoViewModel.saveResource(resourceToSave)
    }
}