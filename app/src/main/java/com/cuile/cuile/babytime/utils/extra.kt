package com.cuile.cuile.babytime.utils

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