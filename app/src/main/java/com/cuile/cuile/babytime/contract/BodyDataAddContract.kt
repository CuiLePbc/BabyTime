package com.cuile.cuile.babytime.contract

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.BodyData

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