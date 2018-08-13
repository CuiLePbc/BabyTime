package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.BodyData
import org.jetbrains.anko.*
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
            restoreBaseLineValue()
            getBaseDatas()
            invalidate()
        }
    var isWeight = false
        set(value) {
            field = value
            restoreBaseLineValue()
            getBaseDatas()
            invalidate()
        }
    var birthDay: Long = 0L
    val bodyDataList: MutableList<BodyData> by Delegates.observable(mutableListOf()){ _, _, _ ->
        invalidate()
    }

    private var baseDatas: MutableList<String> = mutableListOf()

    private var touchedX = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val valueLabelRect = Rect()
    private val timeLabelRect = Rect()

    private var lineSpanV = dip(30).toFloat()
    private var lineSpanH = dip(30).toFloat()
    private val BASE_HEIGHT = 40
    private val TOP_HEIGHT = 85
    private val BASE_WEIGHT = 2
    private val TOP_WEIGHT = 14
    private var realValueNum = 0
    private var textDesLabel = "厘米"

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
        restoreBaseLineValue()

        paint.textSize = sp(12).toFloat()
        paint.getTextBounds("120厘米", 0, "120厘米".length, valueLabelRect)
        paint.getTextBounds("50周", 0, "50周".length, timeLabelRect)

        lineMarginTop = valueLabelRect.height().toFloat() / 2
        touchedX = -5f


        startX = valueLabelRect.width().toFloat() + labelSpace
        startY = lineSpanV * realValueNum + lineMarginTop

        getBaseDatas()
    }

    private fun restoreBaseLineValue() {
        if (isWeight) {
            lineSpanV = dip(22.5f).toFloat()
            realValueNum = (TOP_WEIGHT - BASE_WEIGHT) / 1
            textDesLabel = "千克"
            heightPerOne = lineSpanV / 1
        }
        else {
            lineSpanV = dip(30).toFloat()
            realValueNum = (TOP_HEIGHT - BASE_HEIGHT) / 5
            textDesLabel = "厘米"
            heightPerOne = lineSpanV / 5
        }
    }

    private fun getBaseDatas() {
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

            baseDatas.clear()

            val getedDatas = dataInputReader.readLines()
            baseDatas.addAll(getedDatas.subList(1, getedDatas.size - 1))

            Log.i(javaClass.simpleName, "-----------geted base datas----------")
            Log.i(javaClass.simpleName, "$getedDatas")


        } finally {
            dataInputReader?.close()
            dataInputstream?.close()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        lineSpanH = (measuredWidth - valueLabelRect.width().toFloat() - labelSpace - timeLabelRect.width() / 2) / 10

        widthPerOne = lineSpanH / 35

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
            val baseValue = if (isWeight) BASE_WEIGHT else BASE_HEIGHT
            val currentY = startY - (value - baseValue) * heightPerOne

            canvas?.drawPoint(currentX, currentY, paint)
        }
    }

    private fun drawBaseDatas(canvas: Canvas?) {
        val baseValue = if (isWeight) BASE_WEIGHT else BASE_HEIGHT

        val pathArea = Path()
        for (i in 0..baseDatas.lastIndex) {
            val max = getMaxNum(baseDatas[i])
            val currentX = startX + (i - startDay) * widthPerOne

            val currentY = startY - (max!! - baseValue) * heightPerOne
            if (i == startDay) {
                pathArea.moveTo(currentX, currentY)
            } else {
                pathArea.lineTo(currentX, currentY)
            }

        }
        for (i in 0..baseDatas.lastIndex) {
            val min = getMinNum(baseDatas[baseDatas.lastIndex - i])
            val currentX = startX + (baseDatas.lastIndex - i - startDay) * widthPerOne
            val currentY = startY - (min!! - baseValue) * heightPerOne
            pathArea.lineTo(currentX, currentY)
        }
        pathArea.close()

        paint.color = Color.parseColor("#4D00E3E3")
        paint.style = Paint.Style.FILL
        canvas?.drawPath(pathArea, paint)

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

        paint.color = Color.DKGRAY
        paint.textAlign = Paint.Align.RIGHT
        paint.strokeWidth = 2f

        for (i in 0..realValueNum) {

            val realValue = if (isWeight)
                    TOP_WEIGHT - i * (TOP_WEIGHT - BASE_WEIGHT) / realValueNum
                else
                    TOP_HEIGHT - i * (TOP_HEIGHT - BASE_HEIGHT) / realValueNum


            canvas?.drawLine(
                    textWidth + labelSpace, lineSpanV * i + lineMarginTop,
                    width - labelSpace, lineSpanV * i + lineMarginTop,
                    paint)

            canvas?.drawText("$realValue$textDesLabel", textWidth, lineSpanV * i + lineMarginTop * 2, paint)
        }
    }
}