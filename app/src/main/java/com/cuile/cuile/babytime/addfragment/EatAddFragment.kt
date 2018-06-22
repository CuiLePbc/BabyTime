package com.cuile.cuile.babytime.addfragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.text.Layout
import android.view.View
import android.widget.NumberPicker
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.view.TextDrawable
import kotlinx.android.synthetic.main.fragment_eat_add.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class EatAddFragment: BaseFragment() {
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

    }
    private fun submit(){}



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