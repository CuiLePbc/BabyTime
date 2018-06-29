package com.cuile.cuile.babytime.vp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import org.jetbrains.anko.find
import android.support.v4.view.MenuItemCompat.setContentDescription
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.text.TextUtils
import android.R.attr.name
import android.support.v4.content.ContextCompat



/**
 * Created by cuile on 18-6-29.
 *
 */
class ShowMainRecyclerAdapter(var datas: List<ShowMainItemEntity> = listOf()) : RecyclerView.Adapter<ShowMainRecyclerAdapter.MyViewHolder>() {

    companion object {
        const val FIRST_STICKY_VIEW = 1
        const val HAS_STICKY_VIEW = 2
        const val NONE_STICKY_VIEW = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_show_recycler, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position % 2 === 0) {
            holder.rlContentWrapper.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.bg_color_1))
        } else {
            holder.rlContentWrapper.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.bg_color_2))
        }

        val stickyExampleModel = stickyExampleModels.get(position)
        holder.tvName.setText(stickyExampleModel.name)
        holder.tvGender.setText(stickyExampleModel.gender)
        holder.tvProfession.setText(stickyExampleModel.profession)

        if (position === 0) {
            holder.tvStickyHeader.setVisibility(View.VISIBLE)
            holder.tvStickyHeader.setText(stickyExampleModel.sticky)
            holder.itemView.setTag(FIRST_STICKY_VIEW)
        } else {
            if (!TextUtils.equals(stickyExampleModel.sticky, stickyExampleModels.get(position - 1).sticky)) {
                holder.tvStickyHeader.setVisibility(View.VISIBLE)
                holder.tvStickyHeader.setText(stickyExampleModel.sticky)
                holder.itemView.setTag(HAS_STICKY_VIEW)
            } else {
                holder.tvStickyHeader.setVisibility(View.GONE)
                holder.itemView.setTag(NONE_STICKY_VIEW)
            }
        }
        holder.itemView.contentDescription = stickyExampleModel.sticky
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stickyTV by lazy { itemView.find<TextView>(R.id.recyclerStickyTV) }

        val imageView by lazy { itemView.find<ImageView>(R.id.mainShowItemImg) }
        val titleTV by lazy { itemView.find<TextView>(R.id.mainShowItemTitle) }
        val contentTV by lazy { itemView.find<TextView>(R.id.mainShowItemContent) }
        val timeTV by lazy { itemView.find<TextView>(R.id.mainShowItemTime) }
        val durationTV by lazy { itemView.find<TextView>(R.id.mainShowItemDuration) }

        fun bind(showMainItemEntity: ShowMainItemEntity) {
            with(showMainItemEntity) {
                stickyTV.text = stickyName

                titleTV.text = title
                contentTV.text = content
                timeTV.text = time
                durationTV.text = duration

                Glide.with(imageView.context)
                        .load(image)
                        .into(imageView)
            }
        }
    }
}