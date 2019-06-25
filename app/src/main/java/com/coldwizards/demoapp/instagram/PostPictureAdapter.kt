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
class PostPictureAdapter : RecyclerView.Adapter<PostPictureAdapter.PictureViewHolder>() {

    private val MAX_IMAGE = 9
    var pictures: ArrayList<String> = arrayListOf()
    private var full = false

    // Boolean表示是否添加图片，否则预览图片
    private var itemClickListener: (Int, ArrayList<String>, Boolean) -> Unit =  {c, a,b-> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)

        return PictureViewHolder(parent.context, view)
    }

    override fun getItemViewType(position: Int): Int {
        if (!full && position == itemCount - 1) {
            return 1
        }
        return 0
    }

    override fun getItemCount(): Int {
        full = pictures.size >= MAX_IMAGE
        return if (full) MAX_IMAGE else pictures.size + 1
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            holder.bindView(position, pictures[position])
        } else {
            holder.bindView(position)
        }
    }

    fun getData(): ArrayList<String> {
        return pictures
    }

    fun setData(data: ArrayList<String>) {
        pictures = data
        notifyDataSetChanged()
    }

    fun addData(path: String) {
        pictures.add(path)
        notifyItemChanged(pictures.size-1)
    }

    fun addData(paths: ArrayList<String>) {
        pictures.addAll(paths)
        notifyItemRangeChanged(pictures.size-paths.size, paths.size)
    }

    fun setItemClickListener(listener: (Int, ArrayList<String>, Boolean)-> Unit) {
        itemClickListener = listener
    }

    inner class PictureViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.image)

        fun bindView(position: Int, url: String) {
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context)
                .load(url)
                .into(imageView)

            itemView.setOnClickListener {
                itemClickListener(position, this@PostPictureAdapter.pictures, false)
            }
        }

        fun bindView(position: Int) {
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setImageResource(R.drawable.ic_add_box_black_24dp)

            itemView.setOnClickListener {
                itemClickListener(position, arrayListOf(), true)
            }
        }

    }

}