package com.cuile.cuile.babytime.vp.excretion.add

import android.os.Bundle
import android.view.View
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.ExcretionData
import com.cuile.cuile.babytime.utils.ValueUtils
import kotlinx.android.synthetic.main.fragment_excretion_add.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class ExcretionAddFragment: BaseFragment(), ExcretionAddContract.View {
    override var isActive = isAdded
    override var presenter: ExcretionAddContract.Presenter = ExcretionAddPresenter(this)

    override fun showProgress() {

    }

    override fun stopProgress() {

    }

    override fun turnToShowMainPage() {
        act.onBackPressed()
    }

    override fun initViews() {

        excretionWetHowMany.setSelection(1)
        excretionDryHowMany.setSelection(1)
        excretionDryHowHard.setSelection(2)

        wetColorSeekBar.max = 5
        wetColorSeekBar.progress = 0

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

            val type = when(excretionTypeRadioGroup.checkedRadioButtonId) {
                R.id.wetType -> ValueUtils.ExcretionValue.TYPE_WET
                R.id.driedType ->ValueUtils.ExcretionValue.TYPE_DRIED
                R.id.wetAndDriedType ->ValueUtils.ExcretionValue.TYPE_WET_AND_DRIED
                else -> -1
            }

            val date = Calendar.getInstance().apply { time = excretionDateTV.viewTime }
            val time = Calendar.getInstance().apply { time = excretionTimeTV.viewTime }
            val resultTimeInLong = Calendar.getInstance().apply {
                set(date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH),
                        time.get(Calendar.HOUR_OF_DAY),
                        time.get(Calendar.MINUTE))
            }.timeInMillis


            val excretionData = ExcretionData(
                    name = "",
                    type = type,
                    color = excretionColorView.getCurrentColor(),
                    wetAmount = excretionWetHowMany.selectedItemPosition,
                    driedAmount = excretionDryHowMany.selectedItemPosition,
                    wetStatus = wetColorSeekBar.progress,
                    driedStatus = excretionDryHowHard.selectedItemPosition,
                    time = resultTimeInLong,
                    other = ""
            )

            presenter.saveData(excretionData)
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