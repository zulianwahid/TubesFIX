package com.example.tubescobacoba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ArrayAdapter

class CariItem : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchResults: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_item)

        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)

        searchResults = mutableListOf()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults)
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

    private fun performSearch(query: String) {
        searchResults.clear()

        val allItems = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        for (item in allItems) {
            if (item.contains(query, ignoreCase = true)) {
                searchResults.add(item)
            }
        }

        adapter.notifyDataSetChanged()
    }
}

