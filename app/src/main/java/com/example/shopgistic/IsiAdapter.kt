package com.example.shopgistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class IsiAdapter(val mList: ArrayList<IsiListMainMenu>) :
    RecyclerView.Adapter<IsiAdapter.IsiListViewHolder>() {

    inner class IsiListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val logo : ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IsiListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.isi_kategorigoods, parent, false)

        return IsiListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: IsiListViewHolder, position: Int) {
        val item = mList[position]
        holder.titleTv.text = item.title


        when (item) {
            // Add other cases for different item types if necessary
            else -> {
                Glide.with(holder.itemView)
                    .load(item.logo)
                    .into(holder.logo)
            }
        }
    }
}