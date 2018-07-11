package com.cuile.cuile.babytime.vp.body.show

import com.cuile.cuile.babytime.BaseActivity
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.utils.initToolbar
import kotlinx.android.synthetic.main.activity_body_line.*
import org.jetbrains.anko.toast

class BodyLineActivity : BaseActivity(), BodyLineContract.View {
    override fun showProgress() {}

    override fun stopProgress() {}

    override fun refreshChartView(list: List<BodyData>) {

    }

    override var presenter: BodyLineContract.Presenter = BodyLinePresenter(this)

    override fun getContentViewLayout(): Int = R.layout.activity_body_line

    override fun initViews() {
        setSupportActionBar(bodyLineToolbar)
        bodyLineToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        bodyLineToolbar.setNavigationOnClickListener { finish() }
        bodyLineToolbar.title = getString(R.string.body_lines)

        bodyLineType
    }
}
