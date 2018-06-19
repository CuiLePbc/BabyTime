package com.cuile.cuile.babytime

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewLayout())
        initViews()
    }

    abstract fun getContentViewLayout(): Int
    abstract fun initViews()
}
