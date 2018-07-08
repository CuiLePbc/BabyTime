package com.cuile.cuile.babytime.vp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ShowMainPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val titles = listOf("列表", "图示")

    override fun getItem(position: Int): Fragment =
            if (position == 0) {
                ShowMainListFragment()
            } else {
                ShowMainChartFragment()
            }

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}