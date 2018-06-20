package com.cuile.cuile.babytime.add

import android.os.Bundle
import android.widget.SeekBar
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.ExcretionColors
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_excretion_add.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class ExcretionAddFragment: BaseFragment() {
    override fun initViews() {
        excretionColorSeekBar.max = ExcretionColors.excretionColors.size - 1
        excretionColorSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                excretionColorView.position = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
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