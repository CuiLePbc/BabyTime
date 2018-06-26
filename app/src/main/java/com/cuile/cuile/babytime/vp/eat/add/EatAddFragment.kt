package com.cuile.cuile.babytime.vp.eat.add

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.BottomSheetDialog
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.EatData
import com.cuile.cuile.babytime.utils.DetailsTransition
import com.cuile.cuile.babytime.utils.ValueUtils
import com.cuile.cuile.babytime.view.TextDrawable
import kotlinx.android.synthetic.main.fragment_eat_add.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast
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




    private var isPlaying = false
    private var isStopping = false

    override fun onResume() {
        super.onResume()
        isPlaying = false
        isStopping = false
        eatDurationChron.base = SystemClock.elapsedRealtime()
    }



    override fun initViews() {

        milkTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewSwitch(checkedId)

            when(checkedId) {
                R.id.eatBreast -> {

                }
                R.id.eatDried -> {
                }
                R.id.eatOther -> {
                }
            }
        }

        eatFab_start_or_pause.setOnClickListener {
            switchStartOrPause()
        }

        eatFab_stop_or_submit.setOnClickListener {
            clickStopOrSubmit()
        }


        val hour = (SystemClock.elapsedRealtime() - eatDurationChron.base) / 1000 / 60
        val hourStr = if (hour < 10) "0$hour" else "$hour"
        eatDurationChron.format = "$hourStr:%s"
        eatDurationChron.setOnClickListener {
            // 手动指定时间
            showTimeFillDialog()
        }


        eatmlNP.value = 50
        eatmlNP.minValue = 0
        eatmlNP.maxValue = 300
        eatmlNP.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        eatmlNP.wrapSelectorWheel = false
        eatmlNP.setFormatter { "${it * 5}" }

        eatMotherAmount.max = 5
    }

    private fun showTimeFillDialog() {
        val bottomSheetDialog = BottomSheetDialog(act).apply {
            setCancelable(true)
            setContentView(R.layout.dialog_edit_duration)
        }
        bottomSheetDialog.show()

        val currentDuration = eatDurationChron.text.toString().split(":")

        val hourPicker = bottomSheetDialog.find<NumberPicker>(R.id.hourPicker)
        hourPicker.wrapSelectorWheel = false
        hourPicker.minValue = 0
        hourPicker.maxValue = 12
        hourPicker.value = currentDuration[0].toInt()
        val minutesPicker = bottomSheetDialog.find<NumberPicker>(R.id.minutesPicker)
        minutesPicker.wrapSelectorWheel = true
        minutesPicker.maxValue = 60
        minutesPicker.minValue = 0
        minutesPicker.value = currentDuration[1].toInt()

        bottomSheetDialog.find<Button>(R.id.durationPickSureBtn).setOnClickListener {
            eatDurationChron.stop()
            val hourStr = if(hourPicker.value > 9) hourPicker.value.toString() else "0${hourPicker.value}"
            val minutesStr = if (minutesPicker.value > 9) minutesPicker.value.toString() else "0${minutesPicker.value}"
            eatDurationChron.text = "$hourStr:$minutesStr:00"
            bottomSheetDialog.cancel()
        }
    }

    private fun viewSwitch(checkedId: Int) {
        when(checkedId) {
            R.id.eatBreast -> {
                otherFoodLayout.visibility = View.GONE
                eatMlLayout.visibility = View.GONE
                eatMothreLayout.visibility = View.VISIBLE
            }
            R.id.eatDried -> {
                otherFoodLayout.visibility = View.GONE
                eatMlLayout.visibility = View.VISIBLE
                eatMothreLayout.visibility = View.GONE
            }
            R.id.eatOther -> {
                otherFoodLayout.visibility = View.VISIBLE
                eatMlLayout.visibility = View.VISIBLE
                eatMothreLayout.visibility = View.GONE
            }
        }
    }

    private fun clickStopOrSubmit(){
        val width = eatFab_stop_or_submit.layoutParams.width

        val ani = ValueAnimator.ofFloat(width.toFloat(), width + 410f)
                .apply {
                    duration = 500
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        eatFab_stop_or_submit.layoutParams.width = value.toInt()
                        eatFab_stop_or_submit.requestLayout()
                    }
                    addListener(object: AnimatorListenerAdapter(){
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                            val textDrawable = TextDrawable(context!!).apply {
                                text = getString(R.string.sure_to_add)
                                textAlign = Layout.Alignment.ALIGN_CENTER
                            }
                            eatFab_stop_or_submit.setImageDrawable(textDrawable)
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            eatFab_stop_or_submit
                        }
                    })
                }

        if (isStopping) {
            submit()
        } else {
            isStopping = true
            ani.start()
            stop()
        }
    }

    private fun switchStartOrPause() {
        if (isPlaying) {
            eatFab_start_or_pause.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_play, null))
            isPlaying = false
            pause()
        } else {
            eatFab_start_or_pause.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_pause, null))
            isPlaying = true
            play()
        }
    }

    private fun play(){
        eatDurationChron.start()
    }
    private fun pause(){
        eatDurationChron.stop()
    }
    private fun stop(){
        eatDurationChron.stop()
    }
    private fun submit(){

        val foodType = when(milkTypeRadioGroup.checkedRadioButtonId) {
            R.id.eatBreast -> ValueUtils.EatValue.FOOD_TYPE_BREAST
            R.id.eatDried -> ValueUtils.EatValue.FOOD_TYPE_DRIED
            R.id.eatOther -> ValueUtils.EatValue.FOOD_TYPE_OTHER
            else -> -1
        }

        val nippleSide =
                if (eatNippleSwitch.isChecked) ValueUtils.EatValue.NIPPLE_RIGHT_SIDE
                else ValueUtils.EatValue.NIPPLE_LEFT_SIDE

        val date = Calendar.getInstance().apply { time = eatDateTV.viewTime }
        val time = Calendar.getInstance().apply { time = eatTimeTV.viewTime }
        val resultTimeInLong = Calendar.getInstance().apply {
            set(date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH),
                    time.get(Calendar.HOUR_OF_DAY),
                    time.get(Calendar.MINUTE))
        }.timeInMillis


        val durationStr = eatDurationChron.text.split(":")
        val durationHourBySecond = durationStr[0].toInt() * 60 * 60
        val durationMinutesBySecond = durationStr[1].toInt() * 60
        val durationSeconds = durationStr[2].toInt()
        val duration = durationHourBySecond + durationMinutesBySecond + durationSeconds

        val eatData = EatData(
                name = "",
                foodType = foodType,
                extraFoodName = otherFoodTV.text.toString(),
                milkMl = eatmlNP.value,
                nippleSide = nippleSide,
                time = resultTimeInLong,
                duration = duration,
                other = ""
        )

        presenter.saveData(eatData)

    }



    companion object {
        const val ARG_PARAM = "EatAddFragment_param_key"
        fun getInstance(param: String = ""): EatAddFragment {
            val fragment = EatAddFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }
    override fun getLayout(): Int = R.layout.fragment_eat_add
}