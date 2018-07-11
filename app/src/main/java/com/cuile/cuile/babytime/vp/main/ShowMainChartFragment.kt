package com.cuile.cuile.babytime.vp.main


import android.support.v4.app.Fragment
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import kotlinx.android.synthetic.main.fragment_show_main_chart.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ShowMainChartFragment : BaseFragment(), ShowMainContract.View {
    override var isActive = isAdded
    override fun refreshList(datas: List<ShowMainItemEntity>) {
        showMainChart.datas = datas.toMutableList()
    }

    override fun addItemsToList(datas: List<ShowMainItemEntity>) {
    }

    override fun showProgress() {
    }

    override fun stopProgress() {
    }

    override var presenter: ShowMainContract.Presenter = ShowMainPresenter(this)

    override fun initViews() {
        presenter.requestRecentDaysDatas(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    }

    override fun getLayout() = R.layout.fragment_show_main_chart

}
