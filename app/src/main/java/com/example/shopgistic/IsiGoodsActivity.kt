package com.example.shopgistic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IsiGoodsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<IsiListMainMenu>()
    private lateinit var adapter: IsiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isi_macammacamgoods)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addDataToList()
        adapter = IsiAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun addDataToList() {

    }
}


