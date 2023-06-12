package com.example.shopgistic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ArrayAdapter


class CariItem : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchResults: MutableList<IsiListDetailProduk>
    private lateinit var adapter: IsiDetailProdukAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_item)

        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)

        searchResults = mutableListOf()

        adapter = IsiDetailProdukAdapter(searchResults)
//        { selectedProduct -> addToCart(selectedProduct) }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CariItem)
            this.adapter = adapter
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText)
                return true
            }
        })
    }
//    private fun searchProducts(query: String) {
//        val dbHelper = DatabaseHelper(this)
//        val db = dbHelper.readableDatabase
//
//        val projection = arrayOf(
//            DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME,
//            DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO,
//            DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE,
//            DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT
//        )
//
//        val selection = "${DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME} LIKE ?"
//        val selectionArgs = arrayOf("%$query%")
//
//        val cursor = db.query(
//            DatabaseContract.ProductTable.TABLE_NAME,
//            projection,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null
//        )
//
//        val productList = ArrayList<IsiListDetailProduk>()
//
//        while (cursor.moveToNext()) {
//            val productName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME))
//            val logoUriString = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO))
//            val logoUri = Uri.parse(logoUriString)
//            val price = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE))
//            val weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT))
//
//            val productItem = IsiListDetailProduk(productName, logoUri, price, weight)
//            productList.add(productItem)
//        }
//
//        cursor.close()
//        db.close()
//
//        // Update your RecyclerView adapter with the new list of products
//        adapter.setData(productList)
//    }

    private fun performSearch(query: String) {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME,
            DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO,
            DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE,
            DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT
        )

        val selection = "${DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME} LIKE ?"
        val selectionArgs = arrayOf("%$query%")

        val cursor = db.query(
            DatabaseContract.ProductTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val productList = ArrayList<IsiListDetailProduk>()

        while (cursor.moveToNext()) {
            val productName =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME))
            val logoUriString =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO))
            val logoUri = Uri.parse(logoUriString)
            val price =
                cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE))
            val weight =
                cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT))

            val productItem = IsiListDetailProduk(productName, logoUri, price, weight)
            productList.add(productItem)
        }

        cursor.close()
        db.close()

        // Update the search results and notify the adapter
        searchResults.clear()
        searchResults.addAll(productList)
        adapter.notifyDataSetChanged()
    }

    private fun addToCart(product: IsiListDetailProduk) {
        val intent = Intent(this, Cart::class.java)
        intent.putExtra("selectedProduct", product)
        startActivity(intent)
    }
}




