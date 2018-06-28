package com.cuile.cuile.babytime.vp

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_show_main.*
import kotlinx.android.synthetic.main.layout_fab_menu.*
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

        fabMenuBgFrameLayout.fabMenuItemClicked = {
            if (fabMenuItemClickListener != null) {
                fabMenuItemClickListener?.invoke(it)
            }
        }

        bodyDataCardView.setOnClickListener {
            toast("cardview clicked!")
        }
    }

    override fun onPause() {
        super.onPause()
        fabMenuBgFrameLayout.closeFabMenu()
    }

    override fun getLayout(): Int = R.layout.fragment_show_main
}