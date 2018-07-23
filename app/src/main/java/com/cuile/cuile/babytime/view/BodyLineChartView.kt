package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.BodyData
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.properties.Delegates

/**
 * Created by cuile on 18-7-11.
 *
 */
class BodyLineChartView : View{

    var isBoy = true
        set(value) {
            field = value
            invalidate()
        }
    var isWeight = false
        set(value) {
            field = value
            invalidate()
        }
    var birthDay: Long = 0L
    val bodyDataList: MutableList<BodyData> by Delegates.observable(mutableListOf()){ _, _, _ ->
        invalidate()
    }

    private var touchedX = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val valueLabelRect = Rect()
    private val timeLabelRect = Rect()
    private var lineSpanV = dip(30).toFloat()
    private var lineSpanH = dip(30).toFloat()
    private val BASE_HEIGHT = 40
    private val TOP_HEIGHT = 85
    private val realValueNum = (TOP_HEIGHT - BASE_HEIGHT) / 5
    private var lineMarginTop: Float = 0f

    private var widthPerOne = 0f
    private var heightPerOne = 0f
    private var startX = 0f
    private var startY = 0f

    private val labelSpace = dip(2).toFloat()

    private var canvas: Canvas? = null

    var startDay = 0
    var endDay = 350  //350

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
        paint.getTextBounds("120厘米", 0, "120厘米".length, valueLabelRect)
        paint.getTextBounds("50周", 0, "50周".length, timeLabelRect)

        touchedX = -5f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(widthMeasureSpec, (realValueNum * lineSpanV + valueLabelRect.height().toFloat() * 1.5 + valueLabelRect.height() * 3).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
        drawLines(canvas)

        drawBaseDatas(canvas)

        drawBabyDatas(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            touchedX = event.x
            invalidate()
        }

        return super.onTouchEvent(event)
    }

    private fun drawBabyDatas(canvas: Canvas?) {
        paint.color = Color.BLACK
        paint.strokeWidth = dip(3).toFloat()
        bodyDataList.forEach {
            val value = if (isWeight) {
                it.weight
            } else {
                it.height
            }

            val ageOfDay: Long = (it.date - birthDay) / (1000 * 60 * 60 * 24)


            val currentX = startX + (ageOfDay - startDay) * widthPerOne
            val currentY = startY - (value - BASE_HEIGHT) * heightPerOne

            Log.i("bodyLineChartView", "$ageOfDay,$value")

            canvas?.drawPoint(currentX, currentY, paint)
        }
    }

    private fun drawBaseDatas(canvas: Canvas?) {
        widthPerOne = lineSpanH / 35
        heightPerOne = lineSpanV / 5
        startX = valueLabelRect.width().toFloat() + labelSpace
        startY = lineSpanV * realValueNum + lineMarginTop

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

            val widthPerOne = lineSpanH / 35
            val heightPerOne = lineSpanV / 5
            val startX = valueLabelRect.width().toFloat() + labelSpace
            val startY = lineSpanV * realValueNum + lineMarginTop

            // 去掉标题行
            val datas = dataInputReader.readLines().subList(1, (endDay - startDay + 2 + timeLabelRect.width() / 2 / widthPerOne).toInt())

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

        lineSpanH = (width - valueLabelRect.width().toFloat() - labelSpace - timeLabelRect.width() / 2) / 10

        for (i in 0..10) {
            canvas?.drawLine(valueLabelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    0f,
                    valueLabelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    lineSpanV * realValueNum + valueLabelRect.height().toFloat(),
                    paint)
            canvas?.drawText("${i * 5}周",
                    valueLabelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    lineSpanV * realValueNum + valueLabelRect.height().toFloat() * 2 + labelSpace,
                    paint)
        }

        paint.color = Color.YELLOW
        canvas?.drawLine(touchedX, 0f,
                touchedX, lineSpanV * realValueNum + valueLabelRect.height().toFloat(),
                paint)
    }

    private fun drawHLines(canvas: Canvas?) {
        val textWidth = valueLabelRect.width().toFloat()
        lineMarginTop = valueLabelRect.height().toFloat() / 2

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