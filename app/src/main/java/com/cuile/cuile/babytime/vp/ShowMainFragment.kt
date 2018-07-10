package com.cuile.cuile.babytime.vp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.NotificationCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.MainActivity
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import kotlinx.android.synthetic.main.layout_show_main.*
import kotlinx.android.synthetic.main.layout_fab_menu.*
import kotlinx.android.synthetic.main.fragment_show_main.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast


/**
 * Created by cuile on 18-6-4.
 *
 */
class ShowMainFragment: BaseFragment() {
    companion object {
        const val ARG_PARAM = "ShowMainFragment_param_key"
        fun getInstance(param: String = "", fabMenuItemClickListener: ((Int) -> Unit)?): ShowMainFragment {
            val fragment = ShowMainFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            fragment.fabMenuItemClickListener = fabMenuItemClickListener
            return fragment
        }
    }

    var fabMenuItemClickListener: ((Int) -> Unit)? = null

    override fun initViews() {

        initToolbarAndDrawerLayout()

        setListener()

        initViewPager()

    }


    override fun onPause() {
        super.onPause()
        fabMenuBgFrameLayout.closeFabMenu()
    }

    override fun getLayout(): Int = R.layout.fragment_show_main

    private fun initViewPager() {
        showMainViewPager.adapter = ShowMainPagerAdapter(fragmentManager)
        showMainTabLayout.setupWithViewPager(showMainViewPager)
        showMainViewPager.offscreenPageLimit = 2
    }

    private fun setListener() {
        fabMenuBgFrameLayout.fabMenuItemClicked = {
            if (fabMenuItemClickListener != null) {
                fabMenuItemClickListener?.invoke(it)
            }
        }

        main_navigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.dayChartShow -> toast(it.title)
                R.id.typeChartShow -> toast(it.title)
                R.id.growChartShow -> toast(it.title)
                R.id.pictureShow -> toast(it.title)
                R.id.menu_setting -> toast(it.title)
                R.id.menu_share -> toast(it.title)
                R.id.menu_send -> {
                    val notificationManager: NotificationManager =
                            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val builder = NotificationCompat.Builder(context!!, "babytime_timer")
                            .setSmallIcon(R.drawable.ic_av_timer_black_24dp)
                            .setContentTitle("标题:正在计时")
                            .setOngoing(true)

                    val pendingIntent = PendingIntent.getActivity(
                            context,
                            0,
                            Intent(context, MainActivity::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT)
                    builder.setContentIntent(pendingIntent)
                    notificationManager.notify(0, builder.build())
                }
            }

            true
        }
    }

    private fun initToolbarAndDrawerLayout() {
        show_main_appbar_layout.bringToFront()
        (activity as AppCompatActivity).setSupportActionBar(showMainToolbar)
        showMainToolbar.title = getString(R.string.baby_name)
        showMainToolbar.setTitleTextColor(resources.getColor(android.R.color.white, null))

        val drawerToggle = ActionBarDrawerToggle(act, main_draw_layout, showMainToolbar, R.string.open_drawer, R.string.close_drawer)
        main_draw_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }
}