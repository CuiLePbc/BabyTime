package com.cuile.cuile.babytime.vp

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import kotlinx.android.synthetic.main.fragment_show_main.*
import kotlinx.android.synthetic.main.layout_fab_menu.*


/**
 * Created by cuile on 18-6-4.
 *
 */
class ShowMainFragment: BaseFragment(), ShowMainContract.View {
    override var presenter: ShowMainContract.Presenter = ShowMainPresenter(this)
    private val showMainRecyclerAdapter: ShowMainRecyclerAdapter by lazy { ShowMainRecyclerAdapter() }

    override var isActive: Boolean = isAdded

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



    companion object {
        const val ARG_PARAM = "ShowMainFragment_param_key"
        fun getInstance(param: String = "", fabMenuItemClickListener: ((Int) -> Unit)?): ShowMainFragment {
            val fragment = ShowMainFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            fragment.fabMenuItemClickListener = fabMenuItemClickListener
            return fragment
        }
    }

    var fabMenuItemClickListener: ((Int) -> Unit)? = null


    override fun initViews() {

        initToolbar()

        setFabMenuListener()

        initRecyclerview()


    }

    override fun onResume() {
        super.onResume()
        presenter.requestRecentDaysDatas(3)
    }

    override fun onPause() {
        super.onPause()
        fabMenuBgFrameLayout.closeFabMenu()
    }

    override fun getLayout(): Int = R.layout.fragment_show_main

    private fun setFabMenuListener() {
        fabMenuBgFrameLayout.fabMenuItemClicked = {
            if (fabMenuItemClickListener != null) {
                fabMenuItemClickListener?.invoke(it)
            }
        }
    }

    private fun initToolbar() {
        show_main_appbar_layout.bringToFront()
        showMainToolbar.title = getString(R.string.baby_name)
        showMainToolbar.setTitleTextColor(resources.getColor(android.R.color.white, null))
    }

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