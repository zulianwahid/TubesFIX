package com.example.shopgistic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQL(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

        companion object{
            private const val DATABASE_VERSION = 1
            private const val DATABASE_NAME = "goods.db"
            private const val TBL_GOODS = "tbl_goods"
            private const val ID = "ID"
            private const val NAME = "name"
            private const val QUANTITY = "quantity"


        }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblGoods = ("CREATE TABLE" + TBL_GOODS + "("
                + ID + "INTEGER PRIMARY KEY," + NAME + "TEXT,"
                + QUANTITY + "INTEGER" + ")")
        db?.execSQL(createTblGoods)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_GOODS")
        onCreate(db)
    }

}