package com.coldwizards.demoapp.instagram.view

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.instagram.PictureAdapter
import com.coldwizards.demoapp.instagram.viewmodel.NewPostViewModel
import com.coldwizards.demoapp.model.Post
import com.coldwizards.demoapp.model.User
import kotlinx.android.synthetic.main.fragment_new_post.*


class NewPostFragment : BaseFragment() {

    private val postViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(NewPostViewModel::class.java)
    }

    private val mAdapter = PictureAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_new_post, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_post_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.post -> {
                if (postViewModel.newPost(content.text.toString(),
                        mAdapter.getData()) == 1) {
                    showCenterToast("请登录")
                } else {
                    showLoading("发布中...")
                    Handler().postDelayed({

                        dismissLoading()

                        view!!.findNavController().navigate(R.id.action_newPostFragment_to_postListFragment)
                    }, 1500)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = arguments?.getString("data")?.split(",")?.get(0)
        mAdapter.addData(uri!!)

        pictures.layoutManager = GridLayoutManager(context!!, 3)
        pictures.adapter = mAdapter

        (activity as InsActivity).userViewModel.mUserLiveData.observe(this, Observer {
            postViewModel.mUser = it
        })
    }
}
