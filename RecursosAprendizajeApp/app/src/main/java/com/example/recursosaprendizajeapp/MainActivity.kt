package com.example.recursosaprendizajeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recursosaprendizajeapp.databinding.ActivityMainBinding
import com.example.recursosaprendizajeapp.adapter.ResourceAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.recursosaprendizajeapp.viewmodel.RecursoViewModel
import com.example.recursosaprendizajeapp.model.Recurso

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val recursoViewModel: RecursoViewModel by viewModels()
    private lateinit var resourceAdapter: ResourceAdapter
    private val EXTRA_RECURSO = "extra_recurso"
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            recursoViewModel.fetchResources()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resourceAdapter = ResourceAdapter(emptyList(), {}, {})
        initRecyclerView()

        recursoViewModel.recursosList.observe(this, Observer { recursos ->
            if (recursos.isNotEmpty()) {
                binding.rvRecursos.adapter = ResourceAdapter(
                    recursos,
                    { idToDelete ->
                        recursoViewModel.deleteRecurso(idToDelete)
                        Toast.makeText(this, "Eliminando recurso...", Toast.LENGTH_SHORT).show()
                    },
                    { recursoToEdit ->
                        launchEditResource(recursoToEdit)
                    }
                )
                Toast.makeText(this, "Cargados ${recursos.size} recursos.", Toast.LENGTH_SHORT).show()
            } else {
                binding.rvRecursos.adapter = ResourceAdapter(emptyList(), {}, {})
                showError("No se encontraron recursos disponibles.")
            }
        })

        recursoViewModel.fetchResources()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddResourceActivity::class.java)
            startForResult.launch(intent)
        }

        binding.btnSearch.setOnClickListener {
            val id = binding.etSearchId.text.toString()
            if (id.isNotEmpty()) {
                recursoViewModel.searchResourceById(id)
            } else {
                showError("Por favor, ingrese un ID para buscar.")
            }
        }

        binding.btnLoadAll.setOnClickListener {
            recursoViewModel.fetchResources()
            binding.etSearchId.setText("")
        }
    }

    private fun initRecyclerView() {
        binding.rvRecursos.layoutManager = LinearLayoutManager(this)
        binding.rvRecursos.adapter = resourceAdapter
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun launchEditResource(recurso: Recurso) {
        val intent = Intent(this, AddResourceActivity::class.java).apply {
            putExtra(EXTRA_RECURSO, recurso)
        }
        startForResult.launch(intent)
    }
}