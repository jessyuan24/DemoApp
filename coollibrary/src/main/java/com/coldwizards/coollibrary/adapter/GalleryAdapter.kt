package com.coldwizards.coollibrary.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coldwizards.coollibrary.R
import java.io.File

/**
 * Created by jess on 19-6-19.
 */
class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var data = ArrayList<String>()
    private var selectable = false
    private var selectedImage = HashSet<String>()
    private var maxNum = 6

    private var updateUIListener: (Int, Boolean) -> Unit = { a, b -> } //整数表示已选数量，布尔值表示是否最多

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(parent.context, view)
    }


    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindView(position, data[position], selectable)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    public fun setData(list: ArrayList<String>) {
        data = list

        selectedImage.clear()
        notifyDataSetChanged()
    }

    fun getSelectedImage(): ArrayList<String> {
        val selected = arrayListOf<String>()
        selectedImage.forEach {
            selected.add(it)
        }
        return selected
    }

    fun setSelectable(boolean: Boolean) {
        selectable = boolean

        notifyDataSetChanged()
    }

    fun setMaxNumberOfImage(num: Int) {
        maxNum = num
    }

    fun setUpdateUIListener(listener: (Int, Boolean) -> Unit) {
        updateUIListener = listener
    }

    inner class GalleryViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.image)
        private val maskView = view.findViewById<ImageView>(R.id.selected_mask)
        private val selectedView = view.findViewById<ImageView>(R.id.selected_iv)

        fun bindView(position: Int, path: String, selectable: Boolean) {
            val uri = Uri.fromFile(File(path))
            Glide.with(context).load(uri).into(imageView)

            val contain = selectedImage.contains(path)

            maskView.visibility = if (contain) View.VISIBLE else View.INVISIBLE
            selectedView.visibility = if (selectable) View.VISIBLE else View.INVISIBLE
            selectedView.setImageResource(
                if (contain) R.drawable.ic_check_box_blue_24dp else
                    R.drawable.ic_check_box_outline_blank_black_24dp
            )

            //是否可选
            if (selectable) {
                var max = false
                itemView.setOnClickListener {
                    // 添加或移除图片路径
                    if (selectedImage.contains(path)) {
                        selectedImage.remove(path)

                        this@GalleryAdapter.notifyItemChanged(position)
                    } else {
                        if (selectedImage.size < maxNum) {
                            selectedImage.add(path)

                            this@GalleryAdapter.notifyItemChanged(position)
                        } else {
                            max = true
                        }
                    }


                    updateUIListener(selectedImage.size, max)
                }
            } else {
                itemView.setOnClickListener(null)
            }
        }
    }

}