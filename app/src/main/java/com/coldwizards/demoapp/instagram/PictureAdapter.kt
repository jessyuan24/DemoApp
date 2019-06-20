package com.coldwizards.demoapp.instagram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coldwizards.demoapp.R

/**
 * Created by jess on 19-6-19.
 */
class PictureAdapter: RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    private var pictures: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)

        return PictureViewHolder(parent.context, view)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bindView(pictures[position])
    }

    fun getData(): ArrayList<String> {
        return pictures
    }

    fun setData(data: ArrayList<String>) {
        pictures = data
        notifyDataSetChanged()
    }

    fun addData(url: String) {
        pictures.add(0, url)
        notifyItemChanged(0)
    }

    inner class PictureViewHolder(val context: Context, view: View): RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.image)

        fun bindView(url: String) {
            Glide.with(context)
                .load(url)
                .into(imageView)
        }

    }

}