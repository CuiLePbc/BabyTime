package com.cuile.cuile.babytime.vp.sleep.add

import android.os.Bundle
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.SleepData
import com.cuile.cuile.babytime.utils.ValueUtils
import com.cuile.cuile.babytime.utils.initToolbar
import kotlinx.android.synthetic.main.fragment_sleep_add.*
import org.jetbrains.anko.support.v4.act
import java.util.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class SleepAddFragment: BaseFragment(), SleepAddContract.View {
    override var isActive: Boolean = isAdded
    override var presenter: SleepAddContract.Presenter = SleepAddPresenter(this)

    override fun showProgress() {

    }

    override fun stopProgress() {

    }

    override fun turnToShowMainPage() {
        act.onBackPressed()
    }



    override fun initViews() {
        sleep_add_toolbar.initToolbar(R.string.sleep_add, act)

        sleepFab.setOnClickListener {


            val date = Calendar.getInstance().apply { time = sleepDateTV.viewTime }
            val time = Calendar.getInstance().apply { time = sleepTimeTV.viewTime }
            val resultTimeInLong = Calendar.getInstance().apply {
                set(date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH),
                        time.get(Calendar.HOUR_OF_DAY),
                        time.get(Calendar.MINUTE))
            }.timeInMillis

            val qualityInt = when(sleepQualityGroup.checkedRadioButtonId) {
                R.id.sleepQualityNo -> ValueUtils.SleepValue.QUALITY_NO
                R.id.sleepQualityLess -> ValueUtils.SleepValue.QUALITY_LESS
                R.id.sleepQualityOk -> ValueUtils.SleepValue.QUALITY_OK
                R.id.sleepQualityBetter -> ValueUtils.SleepValue.QUALITY_BETTER
                R.id.sleepQualityGood -> ValueUtils.SleepValue.QUALITY_GOOD
                else -> -1
            }

            val sleepData = SleepData(
                    name = "",
                    time = resultTimeInLong,
                    duration = sleepLongET.text.toString().toInt(),
                    quality = qualityInt,
                    other = ""
            )

            presenter.saveData(sleepData)
        }
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