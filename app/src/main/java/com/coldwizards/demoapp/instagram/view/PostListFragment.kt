package com.coldwizards.demoapp.instagram.view

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coldwizards.coollibrary.view.GalleryActivity
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.instagram.PostAdapter
import com.coldwizards.demoapp.instagram.viewmodel.PostListViewModel
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.FileUtils
import kotlinx.android.synthetic.main.fragment_post_list.*
import java.io.File


/**
 * Created by jess on 19-6-14.
 */
class PostListFragment : BaseFragment() {

    private val REQUEST_CAPTURE_IMAGE = 1
    private val REQUEST_PICK_IMAGE = 0
    private val REQUEST_PERMISSION = 2

    private var mCurrentPosition = 0 // 当前点击的recyclerview的item position
    private var mOffset = 0 // recyclerview滚动offset
    private var mSHowKeyboard = false // 键盘是否已经现实
    private var mHeightOfLayout = 0; // 添加评论layout[commentLayout]的高度，便于动画移动

    private var mUser: User? = null
    private var mImageFile = File("")

    private val adapter = PostAdapter()

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    private val mViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(PostListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.coldwizards.demoapp.R.layout.fragment_post_list, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
        setButtonListener()
        setListener()

        if (!checkPermission()) {
            requestPermission()
        }

        (activity as InsActivity).userViewModel.mUserLiveData.observe(this, Observer {
            mViewModel.mUser = it
            adapter.mUser = it
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            val images = arrayListOf<String>(mImageFile.absolutePath)
            val bundle = Bundle()
            bundle.putStringArrayList("data", images)
            view!!.findNavController().navigate(R.id.action_postListFragment_to_newPostFragment, bundle)
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val images = data?.getStringArrayListExtra(GalleryActivity.SELECTED_IMAGE)
            images?.let {
                val bundle = Bundle()
                bundle.putStringArrayList("data", it)
                view!!.findNavController().navigate(R.id.action_postListFragment_to_newPostFragment, bundle)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.ins_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login -> {
                view!!.findNavController().navigate(R.id.action_postListFragment_to_loginFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * 检查权限
     */
    private fun checkPermission(): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(context!!, it) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }

        return true
    }

    /**
     * 请求需要的权限
     */
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity!!,
            permissions, REQUEST_PERMISSION
        )
    }

    private fun setListener() {
        // 设置显示和隐藏键盘的监听事件
        setHiddenKeyboardListener {
            commentLayout.visibility = View.GONE

            mSHowKeyboard = false

            animatorView(false)
        }
        setShowKeyboardListener {
            commit.isClickable = false
            bookList?.let { view ->
                (view.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(
                        mCurrentPosition,
                        -mOffset + (it + commentLayout.height)
                    )

                // 延时1000S设置这个值
                Handler().postDelayed({
                    mSHowKeyboard = true
                }, 1000)
            }

        }

        // 设置recyclerview滚动事件， 当滚动时隐藏键盘
        bookList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (mSHowKeyboard) {
                    hideKeyboard(activity!!)
                }
            }
        })

        // 下拉刷新监听事件
        refresh_layout.setOnRefreshListener {
            mViewModel.mAllPosts.observe(this, Observer {
                Handler().postDelayed({
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                    refresh_layout.isRefreshing = false

                    showCenterToast("已刷新")
                }, 1000)
            })
        }

        addCommentET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val string = s.toString()
                if (string.isEmpty()) {
                    commit.isClickable = false
                    commit.setTextColor(ContextCompat.getColor(context!!, R.color.grey_500))
                } else {
                    commit.isClickable = true
                    commit.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                }
            }

        })
    }

    /**
     * 初始化按钮事件
     */
    private fun setButtonListener() {
        floatBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder
                .setTitle("选择图片来源")
                .setItems(arrayOf("拍照", "从相册中选择")) { listener, which ->
                    when (which) {
                        0 -> takePhoto()
                        1 -> {
                            val intent = Intent(context!!, GalleryActivity::class.java)
                            intent.putExtra(GalleryActivity.MAX_NUM, 9)
                            intent.putExtra(GalleryActivity.SELECTABLE, true)
                            startActivityForResult(intent, REQUEST_PICK_IMAGE)
                        }
                    }
                }

            builder.create().show()
        }

        commit.setOnClickListener {
            val text = addCommentET.text.toString()
            if (text.isEmpty()) {
                showToast("评论不能空")
            } else {
                if (!mViewModel.isLogin()) {
                    showCenterToast("请登录")
                    return@setOnClickListener
                }

                hideKeyboard(activity!!)

                val post = adapter.currentList?.get(mCurrentPosition)!!
                mViewModel.newComment(post, text)
                adapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * 打开相机拍照
     */
    private fun takePhoto() {
        if (checkPermission()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            mImageFile = FileUtils.createImageFile(context!!)

            if (mImageFile != null) {
                val uri = FileProvider.getUriForFile(
                    context!!,
                    "com.coldwizards.demoapp.provider",
                    mImageFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
            }
        } else {
            requestPermission()
        }
    }

    private fun setRecyclerView() {

        // 设置点击添加评论textview的事件
        adapter.setCommentListener { position, offset ->
            mCurrentPosition = position
            mOffset = offset

            animatorView(true)

            addCommentET.requestFocus()
            showKeyboard(activity!!)
        }

        // 更新post信息
        adapter.likeListener { post, isChecked ->
            mViewModel.update(post)
        }

        bookList.adapter = adapter
        mViewModel.mAllPosts.observe(this, Observer(adapter::submitList))
    }

    /**
     * 当键盘出现或隐藏时，添加评论的layout[commentLayout]和floating Button[floatBtn]向上移动或向下移动
     * 当[moveUp]为true时，[commentLayout]和[floatBtn]向上移动[mHeightOfLayout]个单位
     * 当[moveUp]为false时， [commentLayout]隐藏，[floatBtn]向下移动到原位
     */
    private fun animatorView(moveUp: Boolean) {
        commentLayout.visibility = View.INVISIBLE

        Handler().postDelayed({
            if (mHeightOfLayout == 0) {
                mHeightOfLayout = commentLayout.height
            }

            if (moveUp) {
                val animator = ObjectAnimator.ofFloat(
                    commentLayout, View.TRANSLATION_Y,
                    mHeightOfLayout.toFloat(),
                    0f
                )
                animator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        commentLayout.visibility = View.VISIBLE
                    }

                })
                animator.duration = 400
                animator.start()
            }

            var animator2 = ObjectAnimator.ofFloat(
                floatBtn, View.TRANSLATION_Y,
                0f,
                -mHeightOfLayout.toFloat()
            )

            if (!moveUp) {
                animator2 = ObjectAnimator.ofFloat(
                    floatBtn, View.TRANSLATION_Y,
                    -mHeightOfLayout.toFloat(),
                    0f
                )
            }

            animator2.duration = 400
            animator2.start()
        }, 500)
    }

}