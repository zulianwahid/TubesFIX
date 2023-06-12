package com.example.shopgistic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.SQL_CREATE_CATEGORY_TABLE)
        db.execSQL(DatabaseContract.SQL_CREATE_PRODUCT_TABLE)
        // Create other tables if needed using similar statements
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.CategoryTable.TABLE_NAME}")
        // Drop other tables if needed using similar statements
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "myapp.db"
    }

}