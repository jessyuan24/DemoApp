package com.coldwizards.coollibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Created by jess on 19-6-19.
 */
class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(parent.context, view)
    }


    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class GalleryViewHolder(val context: Context,view: View): RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.image)

        fun bindView() = {
//            Glide.with(context).load()
        }
    }

}