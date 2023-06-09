package com.example.shopgistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val VIEW_TYPE_MAIN_MENU = 0
private const val VIEW_TYPE_GOODS_BERAS = 1


class IsiAdapter(val mList: ArrayList<IsiListMainMenu>) :
    RecyclerView.Adapter<IsiAdapter.IsiListViewHolder>() {

    inner class IsiListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val logo : ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IsiListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = when (viewType) {
            VIEW_TYPE_MAIN_MENU -> inflater.inflate(R.layout.isi_kategorigoods, parent, false)
            VIEW_TYPE_GOODS_BERAS -> inflater.inflate(R.layout.macammacamgoods, parent, false)
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
        return IsiListViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: IsiListViewHolder, position: Int) {
        val item = mList[position]
        holder.titleTv.text = item.title


        when (item) {
            is IsiListMainMenu -> {
                Glide.with(holder.itemView)
                    .load(item.logo)
                    .into(holder.logo)
            }
            // Add other cases for different item types if necessary
        }
    }
}