package com.cuile.cuile.babytime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewLayout())
        initViews()
    }

    abstract fun getContentViewLayout(): Int
    abstract fun initViews()
}
