package com.example.libreria_pde

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelAdapter(
    private var novelList: MutableList<Novel>,
    private val onNovelClick: (Novel) -> Unit,
    private val onDeleteClick: (Novel) -> Unit
) : RecyclerView.Adapter<NovelAdapter.NovelViewHolder>() {

    inner class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewNovelTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewNovelAuthor)
        val textViewYear: TextView = itemView.findViewById(R.id.textViewNovelYear)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.imageViewFavorite)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete) // Botón de eliminar

        fun bind(novel: Novel) {
            textViewTitle.text = novel.title
            textViewAuthor.text = "Autor: ${novel.author}"
            textViewYear.text = "Año: ${novel.year}"

            // Cambiar el ícono de favorito
            favoriteIcon.setImageResource(if (novel.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_border)

            // Establecer clics
            itemView.setOnClickListener { onNovelClick(novel) }
            buttonDelete.setOnClickListener { onDeleteClick(novel) } // Eliminar novela
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.novel_item, parent, false)
        return NovelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val novel = novelList[position]
        holder.bind(novel)

        // Cambiar el color de fondo para diferenciar novelas
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(0xFFB2EBF2.toInt()) // Azul claro
        } else {
            holder.itemView.setBackgroundColor(0xFFB2DFDB.toInt()) // Verde claro
        }
    }

    override fun getItemCount(): Int {
        return novelList.size
    }

    // Método para actualizar la lista de novelas
    fun updateNovels(novels: MutableList<Novel>) {
        this.novelList = novels
        notifyDataSetChanged()
    }
}