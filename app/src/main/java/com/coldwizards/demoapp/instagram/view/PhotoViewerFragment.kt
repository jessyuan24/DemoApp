package com.coldwizards.demoapp.instagram.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.utils.loadImage
import kotlinx.android.synthetic.main.fragment_photo_viewer.*
import java.io.File

/**
 * Created by jess on 19-6-25.
 */
class PhotoViewerFragment : Fragment() {

    companion object {
        fun newInstance(path: String): PhotoViewerFragment {
            val fragment = PhotoViewerFragment()
            var args = Bundle()
            args.putString("image", path)
            fragment.arguments= args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_view.loadImage(context!!,
            Uri.fromFile(File(arguments?.getString("image") ?: "")))
    }

}