package com.cuile.cuile.babytime.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_eat_add.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.textAppearance
import java.util.*

/**
 * Created by cuile on 18-6-21.
 *
 */
class DateSelectTextView: TextView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defstyleAttr: Int): super(context, attrs, defstyleAttr) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defstyleAttr: Int, defStyleRes: Int): super(context, attrs, defstyleAttr, defStyleRes){
        init(context, attrs)
    }

    var mode = 0

    var viewTime = Calendar.getInstance().time

    private fun init(context: Context, attrs: AttributeSet) {

        val attrCount = attrs.attributeCount
        for (i in 0 until attrCount) {
            val attrResId = attrs.getAttributeNameResource(i)
            when(attrResId) {
                R.attr.mode -> {
                    mode = attrs.getAttributeValue(i).toInt()
                }
            }
        }

        setPadding(dip(6), dip(4), dip(6), dip(4))
        textAppearance = android.R.style.TextAppearance_Material_Subhead
        isClickable = true
        isFocusable = true
        background = resources.getDrawable(R.drawable.corner_btn_bg_light_gray, null)


        val calendar = Calendar.getInstance()

        if (mode == 0) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            text = "${year}年${month + 1}月${dayOfMonth}日"

        } else if (mode == 1) {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)
            text = "${hour}:${minutes}"
        }

        setOnClickListener {
            if (mode == 0) {
                DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            text = "${year}年${month + 1}月${dayOfMonth}日"

                            calendar.set(Calendar.YEAR, year)
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            viewTime = calendar.time

                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else if (mode == 1) {
                TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            val hStr = if (hour < 10) "0$hour" else hour.toString()
                            text = "$hStr:$minute"

                            calendar.set(Calendar.HOUR_OF_DAY, hour)
                            calendar.set(Calendar.MINUTE, minute)
                            viewTime = calendar.time

                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                ).show()
            }
        }
    }
}