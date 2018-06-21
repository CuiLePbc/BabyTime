package com.cuile.cuile.babytime.showfragment

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_show_main.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class ShowMainFragment: BaseFragment() {
    companion object {
        const val ARG_PARAM = "ShowMainFragment_param_key"
        fun getInstance(param: String = "", fabMenuItemClickListener: FabMenuItemClickListener): ShowMainFragment {
            val fragment = ShowMainFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            fragment.fabMenuItemClickListener = fabMenuItemClickListener
            return fragment
        }
    }

    private var isFabOpen = false

    var fabMenuItemClickListener: FabMenuItemClickListener? = null


    override fun initViews() {

        mainshowFab.setOnClickListener {
            fabAnimation()
        }

        fabMenuToBodyData.setOnClickListener {
            closeFabMenu()

            fabMenuItemClickListener?.fabMenuItemClicked(R.id.fabMenuToBodyData)
        }
        fabMenuToEatData.setOnClickListener {
            closeFabMenu()
            fabMenuItemClickListener?.fabMenuItemClicked(R.id.fabMenuToEatData)
        }
        fabMenuToExcretionData.setOnClickListener {
            closeFabMenu()
            fabMenuItemClickListener?.fabMenuItemClicked(R.id.fabMenuToExcretionData)
        }
        fabMenuToSleepData.setOnClickListener {
            closeFabMenu()
            fabMenuItemClickListener?.fabMenuItemClicked(R.id.fabMenuToSleepData)
        }

        fabMenuBgFrameLayout.setOnClickListener {
            if (isFabOpen) {
                closeFabMenu()
            }
        }
    }

    private fun fabAnimation(){
        isFabOpen = if (isFabOpen) {
            startAnimation(!isFabOpen)
            setFabMenuClickable(!isFabOpen)
            false
        } else {
            startAnimation(!isFabOpen)
            setFabMenuClickable(!isFabOpen)
            true
        }
    }

    private fun closeFabMenu() {
        startAnimation(false)
        setFabMenuClickable(false)
        isFabOpen = false
    }

    override fun onPause() {
        super.onPause()
        closeFabMenu()
    }

    private fun startAnimation(isOpen: Boolean) {
        val rotateAnimation = if (isOpen) 45f else 0f
        val fabMenuControlNum = if (isOpen) 1f else 0f

        mainshowFab.animate().setDuration(200)
                .rotation(rotateAnimation)
                .start()

        fabMenuToBodyData.animate().setDuration(200)
                .scaleX(fabMenuControlNum)
                .scaleY(fabMenuControlNum)
                .alpha(fabMenuControlNum)
                .start()
        fabMenuToBodyData.animate().setDuration(200)
                .scaleX(fabMenuControlNum)
                .scaleY(fabMenuControlNum)
                .alpha(fabMenuControlNum)
                .start()
        fabMenuToEatData.animate().setDuration(200)
                .scaleX(fabMenuControlNum)
                .scaleY(fabMenuControlNum)
                .alpha(fabMenuControlNum)
                .start()
        fabMenuToExcretionData.animate().setDuration(200)
                .scaleX(fabMenuControlNum)
                .scaleY(fabMenuControlNum)
                .alpha(fabMenuControlNum)
                .start()
        fabMenuToSleepData.animate().setDuration(200)
                .scaleX(fabMenuControlNum)
                .scaleY(fabMenuControlNum)
                .alpha(fabMenuControlNum)
                .start()
    }

    private fun setFabMenuClickable(clickable: Boolean) {
        fabMenuToBodyData.isClickable = clickable
        fabMenuToEatData.isClickable = clickable
        fabMenuToExcretionData.isClickable = clickable
        fabMenuToSleepData.isClickable = clickable
    }

    override fun getLayout(): Int = R.layout.fragment_show_main

    interface FabMenuItemClickListener {
        fun fabMenuItemClicked(clickedId: Int)
    }
}