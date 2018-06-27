package com.cuile.cuile.babytime.vp.eat.add

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.text.Layout
import android.view.View
import android.widget.NumberPicker
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.EatData
import com.cuile.cuile.babytime.utils.ValueUtils
import com.cuile.cuile.babytime.view.TextDrawable
import kotlinx.android.synthetic.main.fragment_eat_add.*
import org.jetbrains.anko.support.v4.act
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
        eatDurationTV.resume()
        resumeStartOrPause()
    }

    override fun onPause() {
        super.onPause()
        if (eatDurationTV.isRunning) {
            eatDurationTV.runningBackground()
        }
    }



    override fun initViews() {

        milkTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewSwitch(checkedId)
        }

        eatFab_start_or_pause.setOnClickListener {
            switchStartOrPause()
        }

        eatFab_stop_or_submit.setOnClickListener {
            clickStopOrSubmit()
        }

        eatmlNP.value = 50
        eatmlNP.minValue = 0
        eatmlNP.maxValue = 300
        eatmlNP.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        eatmlNP.wrapSelectorWheel = false
        eatmlNP.setFormatter { "${it * 5}" }

        eatMotherAmount.max = 5
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

        if (!eatDurationTV.isRunning && !eatDurationTV.isPausing) {
            submit()
        } else {
            ani.start()
            stop()
        }
    }

    private fun resumeStartOrPause() {
        if (!eatDurationTV.isRunning) {
            eatFab_start_or_pause.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_play, null))
        } else {
            eatFab_start_or_pause.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_pause, null))

        }
    }

    private fun switchStartOrPause() {
        if (eatDurationTV.isRunning) {
            eatFab_start_or_pause.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_play, null))
            pause()
        } else {
            eatFab_start_or_pause.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_pause, null))
            play()
        }
    }

    private fun play(){
        eatDurationTV.start()
    }
    private fun pause(){
        eatDurationTV.pause()
    }
    private fun stop(){
        eatDurationTV.stop()
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


        val duration = eatDurationTV.durationSec.toInt()

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