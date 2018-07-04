package com.cuile.cuile.babytime.vp.eat.add

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.EatData
import com.cuile.cuile.babytime.utils.ValueUtils
import com.cuile.cuile.babytime.utils.initToolbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_eat_add.*
import kotlinx.android.synthetic.main.layout_eat_mother_milk.*
import org.jetbrains.anko.commit
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.defaultSharedPreferences
import java.util.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class EatAddFragment: BaseFragment(), EatAddContract.View {
    override var isActive: Boolean = isAdded
    override var presenter: EatAddContract.Presenter = EatAddPresenter(this)

    override fun showProgress() {

    }

    override fun stopProgress() {

    }

    override fun turnToShowMainPage() {
        act.onBackPressed()
    }


    override fun onResume() {
        super.onResume()
        eatLeftDurationTV.resume()
        eatRightDurationTV.resume()
        eatDriedDurationTV.resume()


        if (eatLeftDurationTV.isPausing || eatLeftDurationTV.isRunning
                || eatRightDurationTV.isPausing || eatRightDurationTV.isRunning
                || eatDriedDurationTV.isPausing || eatDriedDurationTV.isRunning) {
            val eatDataJson = defaultSharedPreferences.getString(EAT_DATA_KEY, "")
            if (eatDataJson.isNotBlank())
                resumeUIFromEatData(Gson().fromJson(eatDataJson, EatData::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        if (eatLeftDurationTV.isRunning) {
            eatLeftDurationTV.runningBackground()
        }

        if (eatRightDurationTV.isRunning) {
            eatRightDurationTV.runningBackground()
        }

        if (eatDriedDurationTV.isRunning) {
            eatDriedDurationTV.runningBackground()
        }

        if (eatLeftDurationTV.isPausing || eatLeftDurationTV.isRunning || eatRightDurationTV.isPausing || eatRightDurationTV.isRunning) {
            defaultSharedPreferences.commit {
                putString(EAT_DATA_KEY, Gson().toJson(getEatDataFromUI()))
            }
        }
    }



    override fun initViews() {

        eat_add_toolbar.initToolbar(R.string.eat_add, act)

        milkTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewSwitch(checkedId)
            eatLeftDurationTV.stop()
            eatRightDurationTV.stop()
            eatDriedDurationTV.stop()
        }

        eatLeftDurationTV.attachToImageButton(eatLeftDurationImgBtn)
        eatRightDurationTV.attachToImageButton(eatRightDurationImgBtn)
        eatDriedDurationTV.attachToImageButton(eatDriedDurationImgBtn)

        eatmlNP.value = 50
        eatmlNP.minValue = 0
        eatmlNP.maxValue = 300
        eatmlNP.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        eatmlNP.wrapSelectorWheel = false
        eatmlNP.setFormatter { "${it * 5}" }

        eatAmountLeftSeekBar.max = 4
        eatAmountRightSeekBar.max = 4

        eatDataFab.setOnClickListener { submit() }
    }

    private fun viewSwitch(checkedId: Int) {
        when(checkedId) {
            R.id.eatBreast -> {
                otherFoodLayout.visibility = View.GONE
                eatMlLayout.visibility = View.GONE
                eatMotherMilkLayout.visibility = View.VISIBLE
            }
            R.id.eatDried -> {
                otherFoodLayout.visibility = View.GONE
                eatMlLayout.visibility = View.VISIBLE
                eatMotherMilkLayout.visibility = View.GONE
            }
            R.id.eatOther -> {
                otherFoodLayout.visibility = View.VISIBLE
                eatMlLayout.visibility = View.VISIBLE
                eatMotherMilkLayout.visibility = View.GONE
            }
        }
    }

    private fun submit(){
        eatRightDurationTV.stop()
        eatLeftDurationTV.stop()
        eatDriedDurationTV.stop()
        presenter.saveData(getEatDataFromUI())
    }

    private fun resumeUIFromEatData(eatData: EatData) {

        when(eatData.foodType) {
            ValueUtils.EatValue.FOOD_TYPE_BREAST -> milkTypeRadioGroup.check(R.id.eatBreast)
            ValueUtils.EatValue.FOOD_TYPE_DRIED -> milkTypeRadioGroup.check(R.id.eatDried)
            ValueUtils.EatValue.FOOD_TYPE_OTHER -> milkTypeRadioGroup.check(R.id.eatOther)
        }

        otherFoodTV.text.clear()
        otherFoodTV.text.append(eatData.extraFoodName)
        eatmlNP.value = eatData.amount

        eatAmountLeftSeekBar.progress = eatData.amount
        eatAmountRightSeekBar.progress = eatData.amountR

        eatDateTV.viewTime = Date(eatData.time)
        eatTimeTV.viewTime = Date(eatData.time)
    }

    private fun getEatDataFromUI(): EatData {
        val foodType = when(milkTypeRadioGroup.checkedRadioButtonId) {
            R.id.eatBreast -> ValueUtils.EatValue.FOOD_TYPE_BREAST
            R.id.eatDried -> ValueUtils.EatValue.FOOD_TYPE_DRIED
            R.id.eatOther -> ValueUtils.EatValue.FOOD_TYPE_OTHER
            else -> -1
        }

        val date = Calendar.getInstance().apply { time = eatDateTV.viewTime }
        val time = Calendar.getInstance().apply { time = eatTimeTV.viewTime }

        val resultTimeInLong = Calendar.getInstance().apply {
            set(date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH),
                    time.get(Calendar.HOUR_OF_DAY),
                    time.get(Calendar.MINUTE))
        }.timeInMillis

        val amount =
                if (foodType == ValueUtils.EatValue.FOOD_TYPE_BREAST) eatAmountLeftSeekBar.progress
                else eatmlNP.value


        val duration =
                if (foodType == ValueUtils.EatValue.FOOD_TYPE_BREAST) eatLeftDurationTV.durationSec.toInt()
                else eatDriedDurationTV.durationSec.toInt()
        val durationR = eatRightDurationTV.durationSec.toInt()

        return EatData(
                "",
                foodType,
                otherFoodTV.text.toString(),
                amount,
                eatAmountRightSeekBar.progress,
                resultTimeInLong,
                duration,
                durationR,
                "")
    }



    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        const val ARG_PARAM = "EatAddFragment_param_key"
        const val EAT_DATA_KEY = "eatDataKey"
        fun getInstance(param: String = ""): EatAddFragment {
            val fragment = EatAddFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }
    override fun getLayout(): Int = R.layout.fragment_eat_add
}