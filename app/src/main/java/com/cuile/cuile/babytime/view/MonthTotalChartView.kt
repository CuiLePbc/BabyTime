package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import com.cuile.cuile.babytime.utils.ValueUtils
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by cuile on 18-7-9.
 *
 */
class MonthTotalChartView: View {

    var days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
    var currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1

    var spacingOfLineV = dip(15).toFloat()
    var spacingOfLineH = dip(15).toFloat()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var labelSize = sp(12).toFloat()

    var datas: MutableList<ShowMainItemEntity> by Delegates.observable(mutableListOf()) { _, _, _ ->
        invalidate()
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        paint.strokeCap = Paint.Cap.ROUND

        currentMonth = 2
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, currentMonth - 1)
        days = cal.getActualMaximum(Calendar.DAY_OF_MONTH)



    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, (spacingOfLineV * (days + 1)).toInt())
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLines(canvas)
        drawDatas(canvas)
    }

    private fun drawLines(canvas: Canvas?) {
        getSpace()

        paint.strokeWidth = 2f

        drawDaysLines(canvas)
        drawHoursLines(canvas)
    }

    private fun drawHoursLines(canvas: Canvas?) {
        paint.textAlign = Paint.Align.CENTER
        for (i in 0..24) {
            if (i % 4 == 0 && i != 24 && i != 0) {
                paint.color = Color.RED
                canvas?.drawText("$i 时", spacingOfLineH * (i + 1), spacingOfLineV - dip(2), paint)
                canvas?.drawText("$i 时", spacingOfLineH * (i + 1), spacingOfLineV * (days + 1) - dip(2), paint)
            } else {
                paint.color = Color.DKGRAY
            }

            canvas?.drawLine(spacingOfLineH * (i + 1), spacingOfLineV, spacingOfLineH * (i + 1), spacingOfLineV * days, paint)
        }
    }


    private fun drawDaysLines(canvas: Canvas?) {
        val textHeight = getTextHeight()

        paint.color = Color.DKGRAY
        paint.textAlign = Paint.Align.RIGHT
        for (i in 1..days) {
            canvas?.drawText(i.toString(), spacingOfLineH - dip(1), spacingOfLineV * i + textHeight / 2, paint)
            canvas?.drawLine(spacingOfLineH, spacingOfLineV * i, width.toFloat() - spacingOfLineH, spacingOfLineV * i, paint)
        }
    }


    private fun getTextHeight(): Float {
        val textBounds = Rect()
        paint.textSize = labelSize
        paint.getTextBounds("30", 0, 1, textBounds)

        return textBounds.height().toFloat()
    }

    private fun getSpace() {
        spacingOfLineH = width.toFloat() / 26
        spacingOfLineV = height.toFloat() / (days + 1)
    }

    private fun drawDatas(canvas: Canvas?) {
        paint.strokeWidth = dip(8).toFloat()
        datas.forEach {
            val dayStr = it.stickyName.split("月")[1]
            val day = dayStr.substring(0, dayStr.length - 1).toInt()
            val timeStr = it.time.split(":")
            val time = timeStr[0].toInt() + (timeStr[1].toFloat() / 60)
            val color = when(it.title) {
                ValueUtils.ShowTitleValue.EAT_DATA -> context.getColor(android.R.color.holo_orange_light)
                ValueUtils.ShowTitleValue.EXCRETION_DATA -> context.getColor(android.R.color.holo_green_dark)
                ValueUtils.ShowTitleValue.SLEEP_DATA -> Color.BLACK
                else -> Color.TRANSPARENT
            }

            paint.color = color

            canvas?.drawPoint(spacingOfLineH * (time + 1), spacingOfLineV * day, paint)
        }
    }
}