package com.cuile.cuile.babytime.addfragment

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_excretion_add.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by cuile on 18-6-4.
 *
 */
class ExcretionAddFragment: BaseFragment() {
    override fun initViews() {

        excretionWetHowMany.setSelection(1)
        excretionDryHowMany.setSelection(1)
        excretionDryHowHard.setSelection(2)

        excretionTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.wetType -> {
                    excretionColorView.isEnabled = false
                    excretionColorView.invalidate()
                }
                R.id.driedType -> {
                    excretionColorView.isEnabled = true
                    excretionColorView.invalidate()
                }
                R.id.wetAndDriedType -> {
                    excretionColorView.isEnabled = true
                    excretionColorView.invalidate()
                }
            }
        }

        excretionFab.setOnClickListener {
            toast(excretionColorView.getCurrentColor())
        }
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