package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

/**
 * Created by cuile on 18-7-11.
 *
 */
class BodyLineChartView : View{
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelRect = Rect()
    private var lineSpanV = dip(30).toFloat()

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
    }

    private fun drawLines(canvas: Canvas?) {
        drawHLines(canvas)
        drawVLines(canvas)
    }

    private fun drawVLines(canvas: Canvas?) {
    }

    private fun drawHLines(canvas: Canvas?) {
        val textWidth = labelRect.width().toFloat()
        val labelSpace = dip(2).toFloat()
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