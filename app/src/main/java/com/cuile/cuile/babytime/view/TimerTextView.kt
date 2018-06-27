package com.cuile.cuile.babytime.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.util.AttributeSet
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.utils.sToHMS
import com.cuile.cuile.babytime.utils.two
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.commit
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.find
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by cuile on 18-6-27.
 *
 */
class TimerTextView : TextView {

    companion object {
        private const val IS_RUNNING = "isRunning"
        private const val IS_PAUSING = "isPausing"
        private const val START_TIME = "startTime"
        private const val OLD_DURATION = "oldDuration"
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defstyleAttr: Int): super(context, attrs, defstyleAttr) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defstyleAttr: Int, defStyleRes: Int): super(context, attrs, defstyleAttr, defStyleRes){
        init(context, attrs)
    }

    var hour: Int = 0
    var minute: Int = 0
    var second: Int = 0

    var durationSec: Long by Delegates.observable(0L, { property, oldValue, newValue ->
        if (newValue != oldValue) {
            refreshHMSByDuration()
            refreshShow()
        }
    })


    private var startTime: Long = Calendar.getInstance().timeInMillis / 1000

    var isRunning = false
    var isPausing = false
    private var disposable: Disposable? = null

    @SuppressLint("CheckResult")
    private fun init(context: Context, attrs: AttributeSet) {
        resume()

        setOnClickListener {
            // 手动指定时间
            showTimeFillDialog()
        }
    }

    fun resume() {
        isRunning = context.defaultSharedPreferences.getBoolean(IS_RUNNING, false)
        isPausing = context.defaultSharedPreferences.getBoolean(IS_PAUSING, false)


        when {
            isRunning -> {
                startTime = context.defaultSharedPreferences.getLong(START_TIME, startTime)
                durationSec = Calendar.getInstance().timeInMillis / 1000 - startTime
            }
            isPausing -> durationSec = context.defaultSharedPreferences.getLong(OLD_DURATION, 0)
            else -> refreshShow()
        }

        if (isRunning) start()
    }

    fun start() {
        isRunning = true
        isPausing = false

        if (disposable == null) {
            disposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        durationSec += 1
                    }
        }
    }

    fun stop() {

        isRunning = false
        isPausing = false
        saveStatus()

        if (disposable != null) {
            disposable!!.dispose()
            disposable = null
        }
    }

    fun pause() {
        isRunning = false
        isPausing = true

        saveStatus()

        if (disposable != null) {
            disposable!!.dispose()
            disposable = null
        }
    }

    fun runningBackground() {
        isRunning = true
        isPausing = false

        saveStatus()

        if (disposable != null) {
            disposable!!.dispose()
            disposable = null
        }
    }



    private fun showTimeFillDialog() {
        val bottomSheetDialog = BottomSheetDialog(context).apply {
            setCancelable(true)
            setContentView(R.layout.dialog_edit_duration)
        }
        bottomSheetDialog.show()

        val hourPicker = bottomSheetDialog.find<NumberPicker>(R.id.hourPicker)
        hourPicker.wrapSelectorWheel = false
        hourPicker.minValue = 0
        hourPicker.maxValue = 12
        hourPicker.value = hour
        val minutesPicker = bottomSheetDialog.find<NumberPicker>(R.id.minutesPicker)
        minutesPicker.wrapSelectorWheel = true
        minutesPicker.maxValue = 60
        minutesPicker.minValue = 0
        minutesPicker.value = minute

        bottomSheetDialog.find<Button>(R.id.durationPickSureBtn).setOnClickListener {

            hour = hourPicker.value
            minute = minutesPicker.value
            second = 0

            refreshDurationByHMS()

            bottomSheetDialog.cancel()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun refreshShow() {
        text = "${hour.two()}:${minute.two()}:${second.two()}"
    }

    private fun refreshHMSByDuration(){
        val durTime  = durationSec.sToHMS()
        hour = durTime[0]
        minute = durTime[1]
        second = durTime[2]
    }

    private fun refreshDurationByHMS(){
        durationSec = (hour * 3600 + minute * 60 + second).toLong()
    }

    private fun saveStatus() {
        context.defaultSharedPreferences.commit {
            putBoolean(IS_RUNNING, isRunning)
            putBoolean(IS_PAUSING, isPausing)
            putLong(START_TIME, startTime)
            putLong(OLD_DURATION, durationSec)
        }
    }

}