package com.coldwizards.demoapp.instagram.view

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coldwizards.coollibrary.MyDialog
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.instagram.PhotoViewerAdapter
import kotlinx.android.synthetic.main.activity_photo_viewer.*

/**
 * Created by jess on 19-6-25.
 */
class PhotoViewerActivity : AppCompatActivity() {

    companion object {
        val IMAGES = "images"
    }

    private var images = arrayListOf<String>()
    private var fragments = arrayListOf<Fragment>()
    private var position = 1
    private var adapter: PhotoViewerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewer)

        images = intent.getStringArrayListExtra("images")
        position = intent.getIntExtra("position", 0)

        setToolbar()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.add(Menu.NONE, 0, Menu.NONE, "删除")
        menuItem?.icon = getDrawable(R.drawable.ic_delete_black_24dp)
        menuItem?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                // 退出页面，返回图片路径
                val intent = Intent()
                intent.putExtra(IMAGES, images)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            0 -> {
                MyDialog(this).setTitle("提示")
                    .setMessage("是否删除该图片")
                    .setNegativeButton("取消", R.color.black){}
                    .setPositiveButton("确定", R.color.colorPrimary) {
                        images.removeAt(view_pager.currentItem)
                        adapter?.setFragment(generateFragment(images) as ArrayList<Fragment>)
                        var current = view_pager.currentItem
                        /**
                         * 删除操作时
                         * [current]大于0，则viewpager向前移动一个
                         * [current]等于0， 则viewpager在当前位置
                         * [images]数量等于，则没有数据，退出页面
                         */
                        if (current > 0) {
                            current -= 1
                            view_pager.currentItem = current
                            adapter?.instantiateItem(view_pager, current+1)
                        } else if (images.isEmpty()) {
                            val intent = Intent()
                            intent.putExtra(IMAGES, images)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        } else {
                            view_pager.currentItem = 0
                        }

                    }.create().show()

            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * 通过图片路径新建List of Fragment
     */
    private fun generateFragment(image: ArrayList<String>): ArrayList<PhotoViewerFragment> {
        val fragments = arrayListOf<PhotoViewerFragment>()
        image.forEach {
            fragments.add(PhotoViewerFragment.newInstance(it))
        }

        return fragments
    }

    private fun setToolbar() {
        supportActionBar?.title = "${position+1}/${images.size}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }

    private fun initView() {
        adapter = PhotoViewerAdapter(supportFragmentManager, generateFragment(images) as ArrayList<Fragment>)
        view_pager.adapter = adapter

        view_pager.currentItem = position
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                supportActionBar?.title = "${view_pager.currentItem+1}/${images.size}"
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                supportActionBar?.title = "${position+1}/${images.size}"
            }

            override fun onPageSelected(position: Int) {
                supportActionBar?.title = "${position+1}/${images.size}"
            }
        })
    }

}