package com.cuile.cuile.babytime.vp.body.add

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.db.BodyData

interface BodyAddContract {

    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun showProgress()
        fun stopProgress()
        fun turnToShowMainPage()
    }

    interface Presenter : BasePresenter {
        var isDataMissing: Boolean

        var imageName: String

        fun saveData(bodyData: BodyData)
        fun requestCameraPhoto()
        fun requestLocalPhoto()
    }
}