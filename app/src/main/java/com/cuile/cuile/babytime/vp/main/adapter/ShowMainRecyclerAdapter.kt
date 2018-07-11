package com.cuile.cuile.babytime.vp.main.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import org.jetbrains.anko.find


/**
 * Created by cuile on 18-6-29.
 *
 */
class ShowMainRecyclerAdapter(var datas: MutableList<ShowMainItemEntity> = mutableListOf()) : RecyclerView.Adapter<ShowMainRecyclerAdapter.MyViewHolder>() {

    companion object {
        const val FIRST_STICKY_VIEW = 1
        const val HAS_STICKY_VIEW = 2
        const val NONE_STICKY_VIEW = 3
    }

    fun refreshDatas(datas: List<ShowMainItemEntity>) {
        this.datas.clear()
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    fun addDatas(datas: List<ShowMainItemEntity>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_show_recycler, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemEntity = datas[position]
        holder.bind(itemEntity)

        if (position == 0) {
            holder.stickyTV.visibility = View.VISIBLE
            holder.stickyTV.text = itemEntity.stickyName
            holder.itemView.tag = FIRST_STICKY_VIEW
        } else {
            if (!TextUtils.equals(itemEntity.stickyName, datas[position - 1].stickyName)) {
                holder.stickyTV.visibility = View.VISIBLE
                holder.stickyTV.text = itemEntity.stickyName
                holder.itemView.tag = HAS_STICKY_VIEW
            } else {
                holder.stickyTV.visibility = View.GONE
                holder.itemView.tag = NONE_STICKY_VIEW
            }
        }
        holder.itemView.contentDescription = itemEntity.stickyName
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stickyTV by lazy { itemView.find<TextView>(R.id.itemScrollHeadView) }

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