package com.cuile.cuile.babytime.vp.eat.add

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.db.EatData

/**
 * Created by cuile on 18-6-25.
 *
 */
interface EatAddContract {
    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun showProgress()
        fun stopProgress()
        fun turnToShowMainPage()
    }

    interface Presenter : BasePresenter {
        var isDataMissing: Boolean

        fun saveData(eatData: EatData)
    }
}