package com.cuile.cuile.babytime.vp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cuile.cuile.babytime.R

/**
 * A simple [Fragment] subclass.
 *
 */
class ShowMainChartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_main_chart, container, false)
    }


}
