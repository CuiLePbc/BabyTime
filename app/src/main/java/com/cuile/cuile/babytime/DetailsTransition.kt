package com.cuile.cuile.babytime

import android.content.Context
import android.support.transition.*
import android.util.AttributeSet

/**
 * Created by cuile on 18-6-7.
 *
 */
class DetailsTransition : TransitionSet {
    constructor() {
        init()
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(ChangeImageTransform()).addTransition(ChangeScroll()).addTransition(ChangeClipBounds())
    }
}