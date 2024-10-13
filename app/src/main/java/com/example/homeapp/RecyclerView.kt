package com.example.homeapp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter(
    private val context: Context,
    private val photos: MutableList<Uri>,
    private val onDeleteClick: (Uri) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_view_photo)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.button_delete_photo)

        fun bind(photoUri: Uri) {
            imageView.setImageURI(photoUri)
            deleteButton.setOnClickListener {
                onDeleteClick(photoUri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}
