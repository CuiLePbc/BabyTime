package com.cuile.cuile.babytime.add

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R

/**
 * Created by cuile on 18-6-4.
 *
 */
class SleepAddFragment: BaseFragment() {
    override fun initViews() {

    }

    companion object {
        const val ARG_PARAM = "SleepAddFragment_param_key"
        fun getInstance(param: String = ""): SleepAddFragment {
            val fragment = SleepAddFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }
    override fun getLayout(): Int = R.layout.fragment_sleep_add
}