package com.cuile.cuile.babytime.vp.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.cuile.cuile.babytime.vp.main.ShowMainChartFragment
import com.cuile.cuile.babytime.vp.main.ShowMainListFragment

class ShowMainPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val titles = listOf("列表", "图示")
    private val fragments = listOf<Fragment>(ShowMainListFragment(), ShowMainChartFragment())

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}