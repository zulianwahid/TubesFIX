package com.example.shopgistic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater




class Cart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var cartItems: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartItems = mutableListOf()

        adapter = CartAdapter(cartItems)
        recyclerView.adapter = adapter

        val buttonCheckout: Button = findViewById(R.id.buttonCheckout)
        buttonCheckout.setOnClickListener {
            performCheckout()
        }

        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? IsiListDetailProduk
        if (selectedProduct != null) {
            cartItems.add(selectedProduct.title) // Add the title of the selected product
            adapter.notifyDataSetChanged()
        }
    }
    private fun performCheckout() {
        // Perform checkout logic here
        Toast.makeText(this, "Checkout successful!", Toast.LENGTH_SHORT).show()
        cartItems.clear()
        adapter.notifyDataSetChanged()
    }

    inner class CartAdapter(private val cartItems: List<String>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemNameTextView: TextView = itemView.findViewById(R.id.textViewCartTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = cartItems[position]
            holder.itemNameTextView.text = item
        }

        override fun getItemCount(): Int {
            return cartItems.size
        }
    }
}
