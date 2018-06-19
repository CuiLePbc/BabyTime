package com.cuile.cuile.babytime

import android.os.Bundle

/**
 * Created by cuile on 18-6-4.
 *
 */
class ShowMainFragment: BaseFragment() {
    companion object {
        const val ARG_PARAM = "ShowMainFragment_param_key"
        fun getInstance(param: String = ""): ShowMainFragment {
            val fragment = ShowMainFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }


    override fun initViews() {
    }

    override fun getLayout(): Int = R.layout.fragment_show_main
}