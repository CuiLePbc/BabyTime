package com.cuile.cuile.babytime.vp.excretion.add

import com.cuile.cuile.babytime.model.DataManager
import com.cuile.cuile.babytime.model.DbDataSource
import com.cuile.cuile.babytime.model.db.ExcretionData

/**
 * Created by cuile on 18-6-26.
 *
 */
class ExcretionAddPresenter(private val view: ExcretionAddContract.View): ExcretionAddContract.Presenter {
    override var isDataMissing: Boolean = false
    private val dataManager: DataManager
    init {
        view.presenter = this
        dataManager = DataManager(DbDataSource())
    }

    override fun saveData(excretionData: ExcretionData) {
        view.showProgress()
        dataManager.saveExcretionData(excretionData)
        view.stopProgress()
        view.turnToShowMainPage()
    }
}