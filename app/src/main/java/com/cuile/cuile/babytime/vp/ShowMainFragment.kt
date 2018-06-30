package com.cuile.cuile.babytime.vp

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.i
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import kotlinx.android.synthetic.main.fragment_show_main.*
import kotlinx.android.synthetic.main.layout_fab_menu.*
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast


/**
 * Created by cuile on 18-6-4.
 *
 */
class ShowMainFragment: BaseFragment() {
    companion object {
        const val ARG_PARAM = "ShowMainFragment_param_key"
        fun getInstance(param: String = "", fabMenuItemClickListener: ((Int) -> Unit)?): ShowMainFragment {
            val fragment = ShowMainFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            fragment.fabMenuItemClickListener = fabMenuItemClickListener
            return fragment
        }

        val DATAS = listOf<ShowMainItemEntity>(
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky1", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky1", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky1", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky1", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky2", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky2", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky2", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky2", "title",
                        "content", "time", "duration"),
                ShowMainItemEntity(1, "", "sticky2", "title",
                        "content", "time", "duration")
        )
    }

    var fabMenuItemClickListener: ((Int) -> Unit)? = null


    override fun initViews() {
        showMainToolbar.title = getString(R.string.baby_name)
        showMainToolbar.setTitleTextColor(resources.getColor(android.R.color.white, null))
        fabMenuBgFrameLayout.fabMenuItemClicked = {
            if (fabMenuItemClickListener != null) {
                fabMenuItemClickListener?.invoke(it)
            }
        }

        show_main_appbar_layout.addOnOffsetChangedListener { _, verticalOffset ->
            i("AppBarLayout", "vertical offset: $verticalOffset")
//            showMainRecyclerView.setPadding(0, dip(220) + dip(verticalOffset),
//                    0, 0)
//            showMainScrollHeadView.setPadding(0, dip(220) + dip(verticalOffset),
//                    0, 0)
        }


        val showMainRecyclerAdapter = ShowMainRecyclerAdapter(DATAS)
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

    override fun onPause() {
        super.onPause()
        fabMenuBgFrameLayout.closeFabMenu()
    }

    override fun getLayout(): Int = R.layout.fragment_show_main
}