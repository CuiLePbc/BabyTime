package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.cuile.cuile.babytime.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringReader

/**
 * Created by cuile on 18-7-11.
 *
 */
class BodyLineChartView : View{

    var isBoy = true
    var isWeight = true

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelRect = Rect()
    private var lineSpanV = dip(30).toFloat()
    private var lineSpanH = dip(30).toFloat()

    private val labelSpace = dip(2).toFloat()

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
        setMeasuredDimension(widthMeasureSpec, (8 * lineSpanV + labelRect.height().toFloat() * 1.5 + labelRect.height() * 3).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawLines(canvas)

        drawDatas()
    }

    private fun drawDatas() {
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

        val dataInputstream = resources.openRawResource(baseFileId)
        val datas = InputStreamReader(dataInputstream).readLines()
        datas.forEach {
            val minFloat = getMinNum(it)
            val maxFloat = getMaxNum(it)
        }
    }

    private fun getMinNum(lines: String) = lines.split(" ")[4].toFloatOrNull()
    private fun getMaxNum(lines: String) = lines.split(" ")[18].toFloatOrNull()

    private fun drawLines(canvas: Canvas?) {
        drawHLines(canvas)
        drawVLines(canvas)
    }

    private fun drawVLines(canvas: Canvas?) {
        paint.color = Color.DKGRAY
        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = 2f

        val timeLabelRect = Rect()
        paint.getTextBounds("50周", 0, "50周".length, timeLabelRect)

        lineSpanH = (width - labelRect.width().toFloat() - labelSpace - timeLabelRect.width() / 2) / 10

        for (i in 0..10) {
            canvas?.drawLine(labelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    0f,
                    labelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    lineSpanV * 8 + labelRect.height().toFloat(),
                    paint)
            canvas?.drawText("${i * 5}周",
                    labelRect.width().toFloat() + labelSpace + lineSpanH * i,
                    lineSpanV * 8 + labelRect.height().toFloat() * 2 + labelSpace,
                    paint)
        }
    }

    private fun drawHLines(canvas: Canvas?) {
        val textWidth = labelRect.width().toFloat()
        val lineMarginTop = labelRect.height().toFloat() / 2

        paint.color = Color.DKGRAY
        paint.textAlign = Paint.Align.RIGHT
        paint.strokeWidth = 2f

        for (i in 0..8) {

            val realValue = 120 - i * 10

            canvas?.drawLine(
                    textWidth + labelSpace, lineSpanV * i + lineMarginTop,
                    width - labelSpace, lineSpanV * i + lineMarginTop,
                    paint)

            canvas?.drawText("${realValue}厘米", textWidth, lineSpanV * i + lineMarginTop * 2, paint)
        }
    }
}