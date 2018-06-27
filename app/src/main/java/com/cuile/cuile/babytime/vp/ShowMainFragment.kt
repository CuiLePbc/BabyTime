package com.cuile.cuile.babytime.vp

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.R.id.*
import kotlinx.android.synthetic.main.fragment_show_main.*

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

        fabMenuFrameLayout.fabMenuItemClicked = {
            if (fabMenuItemClickListener != null) {
                fabMenuItemClickListener?.invoke(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fabMenuFrameLayout.closeFabMenu()
    }

    override fun getLayout(): Int = R.layout.fragment_show_main

    interface FabMenuItemClickListener {
        fun fabMenuItemClicked(clickedId: Int)
    }
}