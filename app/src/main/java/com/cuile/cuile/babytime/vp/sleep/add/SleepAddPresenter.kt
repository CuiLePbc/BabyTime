package com.cuile.cuile.babytime.vp.sleep.add

import com.cuile.cuile.babytime.model.DataManager
import com.cuile.cuile.babytime.model.DbDataSource
import com.cuile.cuile.babytime.model.db.SleepData

/**
 * Created by cuile on 18-6-26.
 *
 */
class SleepAddPresenter(private val view: SleepAddContract.View) : SleepAddContract.Presenter {
    override var isDataMissing: Boolean = false
    private val dataManager: DataManager
    init {
        view.presenter = this
        dataManager = DataManager(DbDataSource())
    }

    override fun saveData(sleepData: SleepData) {
        view.showProgress()
        dataManager.saveSleepData(sleepData)
        view.stopProgress()
        view.turnToShowMainPage()
    }
}