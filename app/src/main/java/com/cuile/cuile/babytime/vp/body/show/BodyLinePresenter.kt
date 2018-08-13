package com.cuile.cuile.babytime.vp.body.show

import com.cuile.cuile.babytime.model.DataManager
import com.cuile.cuile.babytime.model.DbDataSource
import java.util.*

/**
 * Created by cuile on 18-7-11.
 *
 */
class BodyLinePresenter(val view: BodyLineContract.View): BodyLineContract.Presenter {

    private val dataManager: DataManager

    init {
        view.presenter = this
        dataManager = DataManager(DbDataSource())
    }

    override fun requestAllBodyDatas() {
        view.showProgress()
        val allBodyDatas = dataManager.getBodyDatasByTimeRange(0, Calendar.getInstance().timeInMillis)
        allBodyDatas?.let { view.refreshChartView(it) }
        view.stopProgress()
    }
}