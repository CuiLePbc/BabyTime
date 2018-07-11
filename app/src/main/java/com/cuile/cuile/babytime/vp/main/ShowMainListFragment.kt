package com.cuile.cuile.babytime.vp.main


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cuile.cuile.babytime.BaseFragment

import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import com.cuile.cuile.babytime.vp.main.adapter.ShowMainRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_show_main_list.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ShowMainListFragment : BaseFragment(), ShowMainContract.View {
    private val showMainRecyclerAdapter: ShowMainRecyclerAdapter by lazy { ShowMainRecyclerAdapter() }


    override var isActive = isAdded
    override fun refreshList(datas: List<ShowMainItemEntity>) {
        showMainRecyclerAdapter.refreshDatas(datas)
    }

    override fun addItemsToList(datas: List<ShowMainItemEntity>) {
        showMainRecyclerAdapter.addDatas(datas)
    }

    override fun showProgress() {
    }

    override fun stopProgress() {
    }

    override var presenter: ShowMainContract.Presenter = ShowMainPresenter(this)

    override fun initViews() {
        initRecyclerview()
        presenter.requestRecentDaysDatas(3)
    }

    override fun getLayout(): Int = R.layout.fragment_show_main_list

    private fun initRecyclerview() {
        showMainRecyclerView.adapter = showMainRecyclerAdapter
        showMainRecyclerView.layoutManager = LinearLayoutManager(context)
        showMainRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val stickyInfoView = showMainRecyclerView.findChildViewUnder(
                        (showMainScrollHeadView.measuredWidth / 2).toFloat(), 5f
                )

                if (stickyInfoView != null && stickyInfoView.contentDescription != null) {
                    showMainScrollHeadView.text = stickyInfoView.contentDescription
                }

                val transInfoView = recyclerView?.findChildViewUnder(
                        (showMainScrollHeadView.measuredWidth / 2).toFloat(),
                        (showMainScrollHeadView.measuredHeight + 1).toFloat())

                if (transInfoView != null && transInfoView.tag != null) {

                    val transViewStatus = transInfoView.tag as Int
                    val dealtY = transInfoView.top - showMainScrollHeadView.measuredHeight

                    if (transViewStatus == ShowMainRecyclerAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.top > 0) {
                            showMainScrollHeadView.translationY = dealtY.toFloat()
                        } else {
                            showMainScrollHeadView.translationY = 0f
                        }
                    } else if (transViewStatus == ShowMainRecyclerAdapter.NONE_STICKY_VIEW) {
                        showMainScrollHeadView.translationY = 0f
                    }
                }
            }
        })
    }
}
