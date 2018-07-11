package com.cuile.cuile.babytime.vp.body.show

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.db.BodyData

/**
 * Created by cuile on 18-7-11.
 *
 */
interface BodyLineContract {
    interface View : BaseView<Presenter> {

        fun showProgress()
        fun stopProgress()
        fun refreshChartView(list: List<BodyData>)
    }

    interface Presenter : BasePresenter {
        fun requestAllBodyDatas()
    }
}