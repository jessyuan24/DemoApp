package com.coldwizards.demoapp.instagram.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.utils.loadImage
import kotlinx.android.synthetic.main.fragment_photo_viewer.*
import java.io.File

/**
 * Created by jess on 19-6-25.
 * [callback]是点击图片的时候回调
 */
class SquarePhotoViewFragment(val image: String, val callback: () -> Unit) : Fragment() {

    companion object {
        fun newInstance(path: String): SquarePhotoViewFragment {
            val fragment = SquarePhotoViewFragment(path){}
            var args = Bundle()
            args.putString("image", path)
            fragment.arguments= args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_square_photo_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        image_view.loadImage(context!!,
//            Uri.fromFile(File(arguments?.getString("image") ?: "")))

        image_view.loadImage(context!!,
            Uri.fromFile(File(image)))

        image_view.setOnClickListener {
            callback()
        }
    }

}