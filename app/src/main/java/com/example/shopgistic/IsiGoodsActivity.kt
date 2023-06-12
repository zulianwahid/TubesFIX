package com.example.shopgistic

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IsiGoodsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val mList = ArrayList<IsiListDetailProduk>()
    private lateinit var adapter: IsiDetailProdukAdapter

    // Database stuff
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var dbRead: SQLiteDatabase

    private val goodsInputLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val userInput = data?.getStringExtra("userInput")
            val pictureUri = data?.getStringExtra("pictureUri") ?: ""
            val price = data?.getFloatExtra("price", 0.0f) ?: 0.0f
            val weight = data?.getFloatExtra("weight", 0.0f) ?: 0.0f

            db.beginTransaction()
            try {
                val values = ContentValues().apply {
                    put(DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME, userInput ?: "")
                    put(DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO, pictureUri)
                    put(DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE, price.toString())
                    put(DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT, weight.toString())
                }

                val rowId = db.insert(DatabaseContract.ProductTable.TABLE_NAME, null, values)
                Log.d("Database Produk", "Inserted row ID: $rowId") // Check if the data is inserted successfully

                // Set the transaction as successful
                db.setTransactionSuccessful()
            } finally {
                // End the transaction
                db.endTransaction()
            }

            addDataToList() // Refresh the list after inserting new data
            adapter.notifyItemInserted(mList.size - 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.macammacamgoods)

        // Initialize database
        dbHelper = DatabaseHelper(applicationContext)
        db = dbHelper.writableDatabase
        dbRead = dbHelper.readableDatabase

        recyclerView = findViewById(R.id.recyclerViewGoods)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Button to launch input menu
        val clickToInputMenu = findViewById<FloatingActionButton>(R.id.addCategory1)
        clickToInputMenu.setOnClickListener {
            val intent = Intent(this, GoodsInput::class.java)
            goodsInputLauncher.launch(intent)
        }

        addDataToList()
        adapter = IsiDetailProdukAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun addDataToList() {
        mList.clear() // Clear the list before populating it again

        dbRead.beginTransaction()
        try {
            val projection = arrayOf(
                DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME,
                DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO,
                DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE,
                DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT
            )

            val cursor = dbRead.query(
                DatabaseContract.ProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            while (cursor.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_NAME))
                val logoUriString = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_LOGO))
                val logoUri = Uri.parse(logoUriString)
                val price = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_PRICE))
                val weight = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseContract.ProductTable.COLUMN_PRODUCT_WEIGHT))


                val item = IsiListDetailProduk(title, logoUri,price,weight)
                mList.add(item)
            }

            cursor.close()

            // Set the transaction as successful
            dbRead.setTransactionSuccessful()
        } finally {
            // End the transaction
            dbRead.endTransaction()
        }
    }
}
