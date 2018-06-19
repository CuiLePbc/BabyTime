package com.cuile.cuile.babytime.add

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R

/**
 * Created by cuile on 18-6-4.
 *
 */
class ExcretionAddFragment: BaseFragment() {
    override fun initViews() {

    }

    companion object {
        const val ARG_PARAM = "ExcretionAddFragment_param_key"
        fun getInstance(param: String = ""): ExcretionAddFragment {
            val fragment = ExcretionAddFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }
    override fun getLayout(): Int = R.layout.fragment_excretion_add
}