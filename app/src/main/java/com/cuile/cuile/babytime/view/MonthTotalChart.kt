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

/**
 * Created by cuile on 18-7-9.
 *
 */
class MonthTotalChart: View {

    var days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
    var currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1

    var spacingOfLineV = dip(15).toFloat()
    var spacingOfLineH = dip(15).toFloat()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var labelSize = sp(12).toFloat()


    var datas: List<ShowMainItemEntity> = listOf(
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EAT_DATA,"content","1:20", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EXCRETION_DATA,"content","2:45", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.SLEEP_DATA,"content","5:10", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EAT_DATA,"content","7:00", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EXCRETION_DATA,"content","11:11", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EAT_DATA,"content","13:12", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EXCRETION_DATA,"content","15:20", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EAT_DATA,"content","18:00", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.SLEEP_DATA,"content","19:50", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EAT_DATA,"content","21:21", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.SLEEP_DATA,"content","23:00", "duration", 0),
            ShowMainItemEntity(0,0,"2018年7月9日",ValueUtils.ShowTitleValue.EXCRETION_DATA,"content","23:50", "duration", 0)
    )

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
        paint.color = Color.DKGRAY
        paint.strokeWidth = 2f
        paint.textSize = labelSize
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

        paint.strokeWidth = 2f

        spacingOfLineH = width.toFloat() / 26
        spacingOfLineV = height.toFloat() / (days + 1)

        val textBounds = Rect()
        paint.getTextBounds("30", 0, 1, textBounds)


        for (i in 1..days) {
            paint.color = Color.DKGRAY
            paint.textAlign = Paint.Align.RIGHT
            canvas?.drawText(i.toString(), spacingOfLineH - dip(1), spacingOfLineV * i + textBounds.height() / 2, paint)
            canvas?.drawLine(spacingOfLineH, spacingOfLineV * i, width.toFloat() - spacingOfLineH, spacingOfLineV * i, paint)
        }

        for (i in 0..24) {
            if (i % 4 == 0 && i != 24 && i != 0) {
                paint.color = Color.RED
                paint.textAlign = Paint.Align.CENTER
                canvas?.drawText("$i 时", spacingOfLineH * (i + 1), spacingOfLineV - dip(2), paint)
                canvas?.drawText("$i 时", spacingOfLineH * (i + 1), spacingOfLineV * (days + 1) - dip(2), paint)
            } else {
                paint.color = Color.DKGRAY
            }

            canvas?.drawLine(spacingOfLineH * (i + 1), spacingOfLineV, spacingOfLineH * (i + 1), spacingOfLineV * days, paint)
        }

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