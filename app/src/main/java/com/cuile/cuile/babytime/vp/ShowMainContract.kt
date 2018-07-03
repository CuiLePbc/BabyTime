package com.cuile.cuile.babytime.vp

import com.cuile.cuile.babytime.BasePresenter
import com.cuile.cuile.babytime.BaseView
import com.cuile.cuile.babytime.model.ShowMainItemEntity

/**
 * Created by cuile on 18-7-3.
 *
 */
interface ShowMainContract {

    interface View: BaseView<Presenter> {
        var isActive: Boolean

        fun refreshList(datas: List<ShowMainItemEntity>)
        fun addItemsToList(datas: List<ShowMainItemEntity>)

        fun showProgress()
        fun stopProgress()
    }

    interface Presenter: BasePresenter {
        var isDataMissing: Boolean


        fun requestRecentDaysDatas(days: Int)
    }
}