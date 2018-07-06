package com.cuile.cuile.babytime.vp.body.add

import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.model.DataManager
import com.cuile.cuile.babytime.model.DbDataSource
import com.cuile.cuile.babytime.utils.PhotoUtil

class BodyAddPresenter (val view: BodyAddContract.View): BodyAddContract.Presenter {


    override var imageName: String = ""

    override var isDataMissing: Boolean = false

    private val dataManager: DataManager

    init {
        view.presenter = this
        dataManager = DataManager(DbDataSource())
    }

    override fun saveData(bodyData: BodyData) {
        view.showProgress()
        dataManager.saveBodyData(bodyData)
        view.stopProgress()
        view.turnToShowMainPage()
    }

    override fun requestCameraPhoto() {
        imageName = System.currentTimeMillis().toString().plus(".jpg")
        PhotoUtil.checkAndTakePhoto(view as BodyAddFragment, imageName)
    }

    override fun requestLocalPhoto() {
        imageName = System.currentTimeMillis().toString().plus(".jpg")
        PhotoUtil.choosePhoto(view as BodyAddFragment)
    }

}