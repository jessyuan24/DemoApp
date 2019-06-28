package com.coldwizards.coollibrary.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.coldwizards.coollibrary.Albums
import com.coldwizards.coollibrary.R
import com.coldwizards.coollibrary.adapter.GalleryAdapter
import com.coldwizards.coollibrary.divider.GridPlacingDecoration
import com.coldwizards.coollibrary.viewmodel.AlbumsViewModel
import kotlinx.android.synthetic.main.activity_gallery.*

/**
 * Created by jess on 19-6-14.
 */
class GalleryActivity : AppCompatActivity() {

    private val TAG = GalleryActivity::class.java.simpleName

    companion object {
        val MAX_NUM = "max_number_of_image"
        val SELECTABLE = "selectable"
        val SELECTED_IMAGE= "selected_image"
    }

    private val viewModel: AlbumsViewModel by lazy {
        ViewModelProviders.of(this).get(AlbumsViewModel::class.java)
    }

    private var albums = arrayListOf(Albums("图库", "", "", 0, false))
    private var mAdapter = GalleryAdapter()
    private var maxNumOfImage = 6
    private var mSelectable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        maxNumOfImage = intent.getIntExtra(MAX_NUM, 6)
        mSelectable = intent.getBooleanExtra(SELECTABLE, false)

        setToolbar()
        setRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        albums.forEachIndexed { index, albums ->
            val menuItem = menu?.add(Menu.NONE, index, Menu.NONE, albums.folderNames)
            menuItem?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra(SELECTED_IMAGE, mAdapter.getSelectedImage())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            0 -> {
                viewModel.getAllAlbums(this).observe(this, Observer {
                    mAdapter.setData(viewModel.getAllImageFromAlbums(it))
                    albums = it
                    albums.add(0, Albums("图库", "", "", 0, false))
                })
            }
            in IntRange(1, albums.size) -> {
                mAdapter.setData(viewModel.getAllImageFromAlbum(albums[item?.itemId!!]))
            }

        }

        supportActionBar?.apply {
            title = item?.title
            subtitle = "选择 0/${maxNumOfImage}"
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        supportActionBar?.apply {
            title = "Albums App"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            subtitle = "选择 0/${maxNumOfImage}"
        }
    }

    private fun setRecyclerView() {
        mAdapter = GalleryAdapter().apply {
            setSelectable(true)
            setUpdateUIListener { num, bool ->
                supportActionBar?.subtitle = "已选择  ${num}/${maxNumOfImage}"
                if (bool) {
                    Toast.makeText(
                        this@GalleryActivity,
                        "最多选择${maxNumOfImage}张图片",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        pictures.apply {
            layoutManager = GridLayoutManager(this@GalleryActivity, 4)
            adapter = mAdapter
            addItemDecoration(
                GridPlacingDecoration(
                    4, 10, false
                )
            )
        }

        viewModel.getAllAlbums(this).observe(this, Observer {
            mAdapter.setData(viewModel.getAllImageFromAlbums(it))
            albums = it
            albums.add(0, Albums("图库", "", "", 0, false))

            invalidateOptionsMenu()
        })
    }


}