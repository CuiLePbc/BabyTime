package com.cuile.cuile.babytime.vp.body.show

import android.view.View
import android.widget.AdapterView
import com.cuile.cuile.babytime.BaseActivity
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.utils.initToolbar
import kotlinx.android.synthetic.main.activity_body_line.*
import org.jetbrains.anko.toast
import java.util.*

class BodyLineActivity : BaseActivity(), BodyLineContract.View {
    override fun showProgress() {}

    override fun stopProgress() {}

    override fun refreshChartView(list: List<BodyData>) {
        bodyLineChart.bodyDataList.addAll(list)
    }

    override var presenter: BodyLineContract.Presenter = BodyLinePresenter(this)

    override fun getContentViewLayout(): Int = R.layout.activity_body_line

    override fun initViews() {
        setSupportActionBar(bodyLineToolbar)
        bodyLineToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        bodyLineToolbar.setNavigationOnClickListener { finish() }
        bodyLineToolbar.title = getString(R.string.body_lines)

        bodyLineType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bodyLineChart.isWeight = position != 0
            }
        }
        bodyLineChart.isBoy = false
        bodyLineChart.birthDay = Calendar.getInstance().apply {
            set(2018, 4, 26, 8, 53)
        }.timeInMillis

        presenter.requestAllBodyDatas()
    }
}
