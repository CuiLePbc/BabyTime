package com.cuile.cuile.babytime.utils

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.CoordinatorLayout.Behavior
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

/**
 * Behavior which should be used by [View]s which can scroll vertically and support
 * nested scrolling to automatically scroll any [AppBarLayout] siblings.
 */
class TextViewBelowToolbarBehavior(context: Context?, attrs: AttributeSet?) : Behavior<TextView>(context, attrs) {
    lateinit var mDependency: NestedScrollView

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: TextView?, dependency: View?): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: TextView?, dependency: View?): Boolean {

        child?.top = dependency?.top!!
        child?.bottom = dependency.top - child?.height!!

        return false
    }
}