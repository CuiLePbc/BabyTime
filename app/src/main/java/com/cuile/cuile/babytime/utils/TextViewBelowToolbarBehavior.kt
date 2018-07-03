package com.cuile.cuile.babytime.utils

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.CoordinatorLayout.Behavior
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import com.cuile.cuile.babytime.MyApplication
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip

/**
 * Behavior which should be used by [View]s which can scroll vertically and support
 * nested scrolling to automatically scroll any [AppBarLayout] siblings.
 */
class TextViewBelowToolbarBehavior(context: Context?, attrs: AttributeSet?) : Behavior<TextView>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: TextView?, dependency: View?): Boolean {

        return dependency is AppBarLayout
    }


    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: TextView?, dependency: View?): Boolean {

        val marginTop = dependency?.bottom!!

        child?.layout(0, marginTop, child.width, child.height + marginTop)

        return true
    }


}