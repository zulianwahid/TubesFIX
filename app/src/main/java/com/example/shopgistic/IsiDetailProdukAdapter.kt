package com.example.shopgistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class IsiDetailProdukAdapter(private var productList: List<IsiListDetailProduk>) : RecyclerView.Adapter<IsiDetailProdukAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.editTextName)
        val editImageView: ImageView = itemView.findViewById(R.id.editImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.isi_macammacamgoods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productList[position]
        holder.titleTextView.text = item.title

        // Load the logo image using Glide or any other image loading library
        Glide.with(holder.itemView)
            .load(item.logo)
            .into(holder.editImageView)
    }

    fun setData(newProductList: List<IsiListDetailProduk>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}
