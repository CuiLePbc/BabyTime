package com.cuile.cuile.babytime

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by cuile on 18-6-3.
 *
 */
class MyApplication: Application() {
    companion object {
        var instance: MyApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }
}