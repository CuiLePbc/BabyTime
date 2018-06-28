package com.cuile.cuile.babytime.view

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.TextView
import com.cuile.cuile.babytime.R
import org.jetbrains.anko.find

class FabMenuFrameLayout : FrameLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    private val fabItemList: MutableList<FloatingActionButton> = mutableListOf()
    private val fabItemLabelList: MutableList<TextView> = mutableListOf()
    private lateinit var mainShowFab: FloatingActionButton
    private var isOpening = false

    var fabMenuItemClicked: ((Int) -> Unit)? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isOpening) return false
        return super.onTouchEvent(event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        fabItemLabelList.add(find(R.id.fabMenuToBodyDataLabel))
        fabItemLabelList.add(find(R.id.fabMenuToEatDataLabel))
        fabItemLabelList.add(find(R.id.fabMenuToExcretionDataLabel))
        fabItemLabelList.add(find(R.id.fabMenuToSleepDataLabel))

        fabItemList.add(find(R.id.fabMenuToBodyData))
        fabItemList.add(find(R.id.fabMenuToEatData))
        fabItemList.add(find(R.id.fabMenuToExcretionData))
        fabItemList.add(find(R.id.fabMenuToSleepData))

        fabItemList.forEach {
            it.setOnClickListener {
                fabMenuItemClicked?.invoke(it.id)
            }
        }

        mainShowFab = find(R.id.mainshowFab)

        mainShowFab.setOnClickListener {
            fabAnimation()
        }

        setOnClickListener { if (isOpening) closeFabMenu() }
    }

    fun closeFabMenu() {
        isOpening = false
        startAnimation(isOpening)
    }

    private fun fabAnimation(){
        isOpening = !isOpening
        startAnimation(isOpening)
    }

    private fun startAnimation(isOpen: Boolean) {
        val rotateAnimation = if (isOpen) 45f else 0f
        val fabMenuControlNum = if (isOpen) 1f else 0f

        mainShowFab.animate().setDuration(200)
                .rotation(rotateAnimation)
                .start()

        for (i in 0 until fabItemList.size) {


            fabItemList[i].animate().setDuration(200)
                    .scaleX(fabMenuControlNum)
                    .scaleY(fabMenuControlNum)
                    .alpha(fabMenuControlNum)
                    .start()

            fabItemLabelList[i].animate().setDuration(200)
                    .scaleX(fabMenuControlNum)
                    .scaleY(fabMenuControlNum)
                    .alpha(fabMenuControlNum)
                    .start()

            fabItemList[i].isClickable = isOpen
            fabItemList[i].isFocusable = isOpen
            fabItemList[i].isEnabled = isOpen
        }
    }
}