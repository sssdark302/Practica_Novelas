
package com.example.libreria_pde

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelAdapter(
    private val novels: List<Novel>,
    private val onNovelClicked: (Novel) -> Unit,
    private val onDeleteClicked: (Novel) -> Unit,
    private val onViewDetailsClick: (Novel) -> Unit
) : RecyclerView.Adapter<NovelAdapter.NovelViewHolder>() {

    inner class NovelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(novel: Novel) {
            val titleTextView = itemView.findViewById<TextView>(R.id.textViewNovelTitle)
            val authorTextView = itemView.findViewById<TextView>(R.id.textViewNovelAuthor)
            val yearTextView = itemView.findViewById<TextView>(R.id.textViewNovelYear)
            val favoriteImageView = itemView.findViewById<ImageView>(R.id.imageViewFavorite)
            val deleteButton = itemView.findViewById<Button>(R.id.buttonDelete)
            val viewDetailsButton = itemView.findViewById<Button>(R.id.buttonViewDetails)

            titleTextView.text = novel.title
            authorTextView.text = novel.author
            yearTextView.text = novel.year.toString()

            favoriteImageView.setImageResource(if (novel.favorite) R.drawable.ic_star_filled else R.drawable.ic_star_border)

            itemView.setOnClickListener {
                onNovelClicked(novel)
            }

          deleteButton.setOnClickListener {
                onDeleteClicked(novel)
            }

            favoriteImageView.setOnClickListener {
                novel.favorite = !novel.favorite
                favoriteImageView.setImageResource(if (novel.favorite) R.drawable.ic_star_filled else R.drawable.ic_star_border)
            }

            // Botón para ver detalles
            viewDetailsButton.setOnClickListener {
                onViewDetailsClick(novel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.novel_item, parent, false)
        return NovelViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        holder.bind(novels[position])
    }

    override fun getItemCount(): Int = novels.size
}
