package com.cuile.cuile.babytime.view

import android.content.Context
import android.graphics.Canvas
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.utils.fabView
import com.cuile.cuile.babytime.vp.ShowMainFragment
import kotlinx.android.synthetic.main.fragment_show_main.*
import org.jetbrains.anko.*

class FabMenuFrameLayout : FrameLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    private val fabItemList: MutableList<FloatingActionButton> = mutableListOf()
    private val fabItemLabelList: MutableList<TextView> = mutableListOf()
    var mainShowFab: FloatingActionButton = FloatingActionButton(context).apply {
        id = View.generateViewId()
        val myLayoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        myLayoutParams.gravity = Gravity.END.and(Gravity.BOTTOM)
        myLayoutParams.rightMargin = dip(16)
        myLayoutParams.bottomMargin = dip(16)
        layoutParams = myLayoutParams

        setImageDrawable(context.getDrawable(R.drawable.ic_add_white_24dp))

        transitionName = context.getString(R.string.fab_shared_name)

        size = FloatingActionButton.SIZE_NORMAL
        elevation = dip(8).toFloat()

        setOnClickListener {
            fabAnimation()
        }
    }
    private var isOpening = false

    var fabMenuItemClicked: ((Int) -> Unit)? = null

    companion object {
        val menuItemLabelList = listOf(
                R.string.body_data,
                R.string.eat_data,
                R.string.excretion_data,
                R.string.sleep_data
        )

        val menuLabelIdList = listOf(
                View.generateViewId(),
                View.generateViewId(),
                View.generateViewId(),
                View.generateViewId()
        )

        val menuFabIdList = listOf(
                View.generateViewId(),
                View.generateViewId(),
                View.generateViewId(),
                View.generateViewId()
        )

        val menuFabDrawableIdList = listOf(
                R.drawable.ic_add_white_24dp,
                R.drawable.ic_add_white_24dp,
                R.drawable.ic_add_white_24dp,
                R.drawable.ic_add_white_24dp
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)





    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        for (i in 0 until menuItemLabelList.size) {

            val t = textView(menuItemLabelList[i]) {
                id = menuLabelIdList[i]
                backgroundColorResource = R.color.material_scrim_color
                textColor = android.R.color.white
                setPadding(dip(2), 0, dip(2), 0)

                val myLayoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                myLayoutParams.width = LayoutParams.WRAP_CONTENT
                myLayoutParams.height = LayoutParams.WRAP_CONTENT
                myLayoutParams.gravity = Gravity.END.and(Gravity.BOTTOM)
                myLayoutParams.rightMargin = dip(68)
                myLayoutParams.bottomMargin = dip(144 + 64 * i)
                layoutParams = myLayoutParams

//                scaleX = 0f
//                scaleY = 0f
//                alpha = 0f
            }
            fabItemLabelList.add(t)


            val f = fabView {
                id = menuFabIdList[i]
                val myLayoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                myLayoutParams.width = LayoutParams.WRAP_CONTENT
                myLayoutParams.height = LayoutParams.WRAP_CONTENT
                myLayoutParams.gravity = Gravity.END.and(Gravity.BOTTOM)
                myLayoutParams.rightMargin = dip(24)
                myLayoutParams.bottomMargin = dip(104 + 64 * i)
                layoutParams = myLayoutParams

//                scaleX = 0f
//                scaleY = 0f
//                alpha = 0f
//
//                isClickable = false
//                isFocusable = false

                setImageDrawable(context.getDrawable(menuFabDrawableIdList[i]))

                size = FloatingActionButton.SIZE_MINI
                elevation = dip(8).toFloat()

                if (fabMenuItemClicked != null){
                    fabMenuItemClicked?.invoke(i)
                }
            }
            fabItemList.add(f)
        }


        mainShowFab = fabView {
            id = View.generateViewId()
            val myLayoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            myLayoutParams.width = LayoutParams.WRAP_CONTENT
            myLayoutParams.height = LayoutParams.WRAP_CONTENT
            myLayoutParams.gravity = Gravity.END.and(Gravity.BOTTOM)
            myLayoutParams.rightMargin = dip(16)
            myLayoutParams.bottomMargin = dip(16)
            layoutParams = myLayoutParams

            setImageDrawable(context.getDrawable(R.drawable.ic_add_white_24dp))

            transitionName = context.getString(R.string.fab_shared_name)

            size = FloatingActionButton.SIZE_NORMAL
            elevation = dip(8).toFloat()

            setOnClickListener {
                fabAnimation()
            }

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