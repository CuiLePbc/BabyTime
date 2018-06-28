package com.cuile.cuile.babytime.utils

import android.app.Activity
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewManager
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_bodydata_add.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.act

/**
 * Created by cuile on 18-6-27.
 *
 */
fun Int.two() =
        when {
            this in 0..9 -> "0$this"
            this > 9 -> this.toString()
            else -> ""
        }

fun Long.sToHMS(): Array<Int> {

    var hour = 0
    var minute = 0
    var second = 0

    if (this > 0) {
        minute = (this / 60).toInt()

        hour = minute / 60
        minute %= 60
        second = (this % 60).toInt()
    }

    return arrayOf(hour, minute, second)
}

fun Toolbar.initToolbar(title: Int, activity: Activity) {
    this.title = context.getString(title)
    navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
    setNavigationOnClickListener { activity.onBackPressed() }
}