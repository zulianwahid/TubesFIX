package com.example.shopgistic

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    // Database stuff
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var dbRead: SQLiteDatabase

    private val goodsInputLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val userInput = data?.getStringExtra("userInput")
            val pictureUri = data?.getStringExtra("pictureUri")     //ini masih string

            db.beginTransaction()
            try {
                val values = ContentValues().apply {
                    put(DatabaseContract.CategoryTable.COLUMN_TITLE, userInput ?: "")
                    put(DatabaseContract.CategoryTable.COLUMN_LOGO, pictureUri ?: "")
                }

                val rowId = db.insert(DatabaseContract.CategoryTable.TABLE_NAME, null, values)
                //Log.d("Database", "Inserted row ID: $rowId")

                // Set the transaction as successful
                db.setTransactionSuccessful()
            }
            finally {
                // End the transaction
                db.endTransaction()

                // Close the database connections
                //db.close()
                //dbRead.close()
            }

            addDataToList() // Refresh the list after inserting new data

            adapter.notifyItemInserted(mList.size - 1)
        }
    }

    private lateinit var recyclerView: RecyclerView
    private val mList = ArrayList<IsiListMainMenu>()
    private lateinit var adapter: IsiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database
        dbHelper = DatabaseHelper(applicationContext)
        db = dbHelper.writableDatabase
        dbRead = dbHelper.readableDatabase

        // Button to navigate to another menu
        val buttonClick = findViewById<Button>(R.id.button2)
        buttonClick.setOnClickListener {
            val intent = Intent(this, IsiGoodsActivity::class.java)
            startActivity(intent)
        }

        // Button to launch input menu
        val clickToInputMenu = findViewById<FloatingActionButton>(R.id.addCategory)
        clickToInputMenu.setOnClickListener {
            val intent = Intent(this, GoodsCategoryInput::class.java)
            goodsInputLauncher.launch(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the list with data from the database
        addDataToList()

        adapter = IsiAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun addDataToList() {
        mList.clear() // Clear the list before populating it again

        dbRead.beginTransaction()
        try {
            val projection = arrayOf(
                DatabaseContract.CategoryTable.COLUMN_TITLE,
                DatabaseContract.CategoryTable.COLUMN_LOGO
            )

            val cursor = dbRead.query(
                DatabaseContract.CategoryTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            while (cursor.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CategoryTable.COLUMN_TITLE))
                val logoUriString = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CategoryTable.COLUMN_LOGO))
                val logoUri = Uri.parse(logoUriString)

                val item = IsiListMainMenu(title, logoUri)
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
