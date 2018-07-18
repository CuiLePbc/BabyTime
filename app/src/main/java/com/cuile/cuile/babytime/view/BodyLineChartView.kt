package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.cuile.cuile.babytime.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by cuile on 18-7-11.
 *
 */
class BodyLineChartView : View{

    var isBoy = true
    var isWeight = false

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelRect = Rect()
    private var lineSpanV = dip(30).toFloat()
    private var lineSpanH = dip(30).toFloat()
    private val BASE_HEIGHT = 40
    private val TOP_HEIGHT = 85
    private val realValueNum = (TOP_HEIGHT - BASE_HEIGHT) / 5
    private var lineMarginTop: Float = 0f

    private val labelSpace = dip(2).toFloat()

    var startDay = 0
    var endDay = 400  //350

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
        paint.textSize = sp(12).toFloat()
        paint.getTextBounds("120厘米", 0, "120厘米".length, labelRect)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(widthMeasureSpec, (realValueNum * lineSpanV + labelRect.height().toFloat() * 1.5 + labelRect.height() * 3).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawLines(canvas)

        drawDatas(canvas)
    }

    private fun drawDatas(canvas: Canvas?) {
        val baseFileId = if (isBoy) {
            if (isWeight) {
                R.raw.wfa_boys_p_exp
            } else {
                R.raw.lhfa_boys_p_exp
            }
        } else {
            if (isWeight) {
                R.raw.wfa_girls_p_exp
            } else {
                R.raw.lhfa_girls_p_exp
            }
        }

        var dataInputstream: InputStream? = null
        var dataInputReader: InputStreamReader? = null
        try {
            dataInputstream = resources.openRawResource(baseFileId)
            dataInputReader = InputStreamReader(dataInputstream)

            // 去掉标题行
            val datas = dataInputReader.readLines().subList(1, endDay - startDay + 2)

            val widthPerOne = lineSpanH / 35
            val heightPerOne = lineSpanV / 5
            val startX = labelRect.width().toFloat() + labelSpace
            val startY = lineSpanV * realValueNum + lineMarginTop

            Log.i("min","${datas.last().split("\t")[4]} -- " +
                    "${startY - (datas.last().split("\t")[4].toFloat() - BASE_HEIGHT) * heightPerOne}")
            Log.i("max","${datas.last().split("\t")[18]} -- " +
                "${startY - (datas.last().split("\t")[18].toFloat() - BASE_HEIGHT) * heightPerOne}")

            val pathArea = Path()
            for (i in 0..datas.lastIndex) {
                val max = getMaxNum(datas[i])
                val currentX = startX + (i - startDay) * widthPerOne
                val currentY = startY - (max!! - BASE_HEIGHT) * heightPerOne
                if (i == startDay) {
                    pathArea.moveTo(currentX, currentY)
                } else {
                    pathArea.lineTo(currentX, currentY)
                }

            }
            for (i in 0..datas.lastIndex) {
                val min = getMinNum(datas[datas.lastIndex - i])
                val currentX = startX + (datas.lastIndex - i - startDay) * widthPerOne
                val currentY = startY - (min!! - BASE_HEIGHT) * heightPerOne
                pathArea.lineTo(currentX, currentY)
            }
            pathArea.close()

            paint.color = Color.parseColor("#4D00E3E3")
            paint.style = Paint.Style.FILL
            canvas?.drawPath(pathArea, paint)

        } finally {
            dataInputReader?.close()
            dataInputstream?.close()
        }




    }

    private fun getMinNum(lines: String) = lines.split("\t")[4].toFloatOrNull()
    private fun getMaxNum(lines: String) = lines.split("\t")[18].toFloatOrNull()

    private fun drawLines(canvas: Canvas?) {
        paint.color = Color.DKGRAY
        paint.style = Paint.Style.FILL
        drawHLines(canvas)
        drawVLines(canvas)
    }

    private fun drawVLines(canvas: Canvas?) {
        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = 2f

        val timeLabelRect = Rect()
        paint.getTextBounds("50周", 0, "50周".length, timeLabelRect)

        lineSpanH = (width - labelRect.width().toFloat() - labelSpace - timeLabelRect.width() / 2) / 10

        for (i in 0..10) {
            canvas?.drawLine(labelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    0f,
                    labelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    lineSpanV * realValueNum + labelRect.height().toFloat(),
                    paint)
            canvas?.drawText("${i * 5}周",
                    labelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    lineSpanV * realValueNum + labelRect.height().toFloat() * 2 + labelSpace,
                    paint)
        }
    }

    private fun drawHLines(canvas: Canvas?) {
        val textWidth = labelRect.width().toFloat()
        lineMarginTop = labelRect.height().toFloat() / 2

        paint.color = Color.DKGRAY
        paint.textAlign = Paint.Align.RIGHT
        paint.strokeWidth = 2f

        for (i in 0..realValueNum) {

            val realValue = TOP_HEIGHT - i * (TOP_HEIGHT - BASE_HEIGHT) / realValueNum

            canvas?.drawLine(
                    textWidth + labelSpace, lineSpanV * i + lineMarginTop,
                    width - labelSpace, lineSpanV * i + lineMarginTop,
                    paint)

            canvas?.drawText("${realValue}厘米", textWidth, lineSpanV * i + lineMarginTop * 2, paint)
        }
    }
}