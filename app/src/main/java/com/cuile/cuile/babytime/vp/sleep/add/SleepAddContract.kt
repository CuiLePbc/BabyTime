package com.cuile.cuile.babytime.vp.sleep.add

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.db.SleepData

/**
 * Created by cuile on 18-6-26.
 *
 */
interface SleepAddContract {
    interface View: BaseView<Presenter> {
        var isActive: Boolean
        fun showProgress()
        fun stopProgress()
        fun turnToShowMainPage()
    }
    interface Presenter: BasePresenter {
        var isDataMissing: Boolean

        fun saveData(sleepData: SleepData)
    }
}