package com.cuile.cuile.babytime.vp.eat.add

import com.cuile.cuile.babytime.model.DataManager
import com.cuile.cuile.babytime.model.DbDataSource
import com.cuile.cuile.babytime.model.db.EatData

/**
 * Created by cuile on 18-6-25.
 *
 */
class EatAddPresenter(val view: EatAddContract.View) : EatAddContract.Presenter {
    override var isDataMissing: Boolean = false
    private val dataManager: DataManager
    init {
        view.presenter = this
        dataManager = DataManager(DbDataSource())
    }

    override fun saveData(eatData: EatData) {
        view.showProgress()
        dataManager.saveEatData(eatData)
        view.stopProgress()
        view.turnToShowMainPage()
    }
}