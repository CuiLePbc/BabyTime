package com.cuile.cuile.babytime.vp.body.add

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.db.BodyData

interface BodyDataAddContract {

    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun showProgress()
        fun stopProgress()
        fun turnToShowMainPage()
    }

    interface Presenter : BasePresenter {
        var isDataMissing: Boolean

        fun saveData(bodyData: BodyData)
    }
}