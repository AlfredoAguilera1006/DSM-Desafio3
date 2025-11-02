package com.example.recursosaprendizajeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.recursosaprendizajeapp.R
import com.example.recursosaprendizajeapp.databinding.ItemRecursoBinding
import com.example.recursosaprendizajeapp.model.Recurso

class ResourceAdapter(
    private val resources: List<Recurso>,
    private val onDeleteClicked: (String) -> Unit,
    private val onResourceClicked: (Recurso) -> Unit
) : RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val binding = ItemRecursoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resources[position])
    }

    override fun getItemCount(): Int = resources.size


    inner class ResourceViewHolder(private val binding: ItemRecursoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recurso: Recurso) {
            binding.tvTitulo.text = recurso.titulo
            binding.tvDescripcion.text = recurso.descripcion
            binding.tvTipo.text = "Tipo: ${recurso.tipo}"
            binding.tvEnlace.text = "Enlace: ${recurso.enlace}"

            val placeholderResId = R.drawable.ic_recurso_placeholder
            val imageUrl = recurso.imagen

            if (imageUrl.isNullOrEmpty()) {
                binding.ivRecurso.setImageResource(placeholderResId)
            } else {
                Picasso.get()
                    .load(imageUrl)
                    .error(placeholderResId)
                    .placeholder(placeholderResId)
                    .into(binding.ivRecurso)
            }

            binding.btnDelete.setOnClickListener {
                recurso.id?.let { id ->
                    onDeleteClicked(id)
                }
            }

            itemView.setOnClickListener {
                onResourceClicked(recurso)
            }
        }
    }
}