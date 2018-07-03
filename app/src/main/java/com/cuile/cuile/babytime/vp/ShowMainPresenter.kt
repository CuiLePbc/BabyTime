package com.cuile.cuile.babytime.vp

import com.cuile.cuile.babytime.model.DataManager
import com.cuile.cuile.babytime.model.DbDataSource
import java.util.*

class ShowMainPresenter(private val view: ShowMainContract.View) : ShowMainContract.Presenter {
    override var isDataMissing: Boolean = false
    private val dataManager: DataManager
    init {
        view.presenter = this
        dataManager = DataManager(DbDataSource())
    }

    override fun requestRecentDaysDatas(days: Int) {
        view.showProgress()

        val nowTime = Calendar.getInstance().timeInMillis
        val fromTime = nowTime - days * 24 * 60 * 60 * 1000    // 进几天数据
        val datas = dataManager.getDatasByTimeRange(fromTime, nowTime)

        view.stopProgress()

        view.refreshList(datas)
    }

}
