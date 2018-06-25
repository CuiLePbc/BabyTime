package com.cuile.cuile.babytime.vp.excretion.add

import android.os.Bundle
import android.view.View
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
            switchViewById(checkedId)

            when(checkedId) {
                R.id.wetType -> {

                }
                R.id.driedType -> {

                }
                R.id.wetAndDriedType -> {

                }
            }
        }

        excretionFab.setOnClickListener {
            toast(excretionColorView.getCurrentColor())
        }
    }

    private fun switchViewById(checkedId: Int) {
        when(checkedId) {
            R.id.wetType -> {
                excretionColorView.visibility = View.GONE
                driedColorLabel.visibility = View.GONE

                wetColorLabel.visibility = View.VISIBLE
                wetColorLayout.visibility = View.VISIBLE

                excretionWetHowMany.visibility = View.VISIBLE
                excretionDryHowMany.visibility = View.GONE

                excretionDryHowHardLabel.visibility = View.GONE
                excretionDryHowHard.visibility = View.GONE
            }
            R.id.driedType -> {
                excretionColorView.visibility = View.VISIBLE
                driedColorLabel.visibility = View.VISIBLE

                wetColorLabel.visibility = View.GONE
                wetColorLayout.visibility = View.GONE
                excretionWetHowMany.visibility = View.GONE
                excretionDryHowMany.visibility = View.VISIBLE

                excretionDryHowHardLabel.visibility = View.VISIBLE
                excretionDryHowHard.visibility = View.VISIBLE
            }
            R.id.wetAndDriedType -> {
                excretionColorView.visibility = View.VISIBLE
                driedColorLabel.visibility = View.VISIBLE

                wetColorLabel.visibility = View.VISIBLE
                wetColorLayout.visibility = View.VISIBLE
                excretionWetHowMany.visibility = View.VISIBLE
                excretionDryHowMany.visibility = View.VISIBLE

                excretionDryHowHardLabel.visibility = View.VISIBLE
                excretionDryHowHard.visibility = View.VISIBLE
            }
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