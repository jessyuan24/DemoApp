package com.coldwizards.demoapp.instagram

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * Created by jess on 19-6-25.
 */
class PhotoViewerAdapter(val fragmentManager: FragmentManager,
                         var fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any): Int {


        return PagerAdapter.POSITION_NONE
    }

    /**
     * 强制更新adapter的数据
     */
    fun setFragment(fragments: ArrayList<Fragment>) {
        var ft = fragmentManager.beginTransaction()
        this.fragments.forEach {
            ft.remove(it)
        }

        ft.commit()
        fragmentManager.executePendingTransactions()

        this.fragments = fragments
        notifyDataSetChanged()
    }

}