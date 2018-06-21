package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.cuile.cuile.babytime.utils.ExcretionColors
import com.cuile.cuile.babytime.R
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.dip

/**
 * view to choose color of excretion.
 */
class ExcretionColorView : View {
    private val colorPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL } }
    private var colorWidth = 0f
    private var colorHeight = 0f


    var icRadius: Float = dip(8).toFloat()


    var icSpace: Float = dip(8).toFloat()


    var position: Int = 0
        set(value) {
            field = value
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
        position = 0
        icSpace = dip(2).toFloat()
        icRadius = dip(8).toFloat()


    }

    fun getCurrentColor(): String = ExcretionColors.excretionColors[position]

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        colorWidth = measuredWidth.toFloat() / ExcretionColors.excretionColors.size
        colorHeight = measuredHeight.toFloat() - 3 * icRadius - icSpace

        icRadius = colorWidth / 2

        ExcretionColors.excretionColors.forEachWithIndex { i, s ->
            colorPaint.color = Color.parseColor(s)
            colorPaint.style = Paint.Style.FILL
            canvas.drawRect(0 + colorWidth * i, 0f, colorWidth + colorWidth * i, colorHeight, colorPaint)
        }


        val topX = colorWidth / 2 + colorWidth * position
        val topY = colorHeight + icSpace

        val circleX = topX
        val circleY = topY + icRadius * 2

        val path = Path()
        path.moveTo(topX, topY)
        path.arcTo(circleX - icRadius, circleY - icRadius, circleX + icRadius, circleY + icRadius, -30f, 240f, false)
        path.close()

        colorPaint.color = Color.parseColor(ExcretionColors.excretionColors[position])
        colorPaint.style = Paint.Style.FILL
        canvas.drawPath(path, colorPaint)

        colorPaint.color = Color.BLUE
        colorPaint.style = Paint.Style.STROKE
        canvas.drawPath(path, colorPaint)

        canvas.drawRect(0 + colorWidth * position, 0f, colorWidth + colorWidth * position, colorHeight, colorPaint)
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)

        if (isEnabled) {
            canvas?.drawColor(Color.TRANSPARENT)
        } else {
            canvas?.drawColor(resources.getColor(R.color.material_scrim_color, null))
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event == null || !isEnabled) return false

        val touchX = event.x
        val touchY = event.y
        if (touchY > colorHeight) {
            position = widthToPosition(touchX)
        }
        return true
    }

    private fun widthToPosition(touchX: Float): Int =
            if (colorWidth != 0f) {
                Math.floor((touchX / colorWidth).toDouble()).toInt()
            } else 0
}
