package com.example.shopgistic

import android.provider.BaseColumns

object DatabaseContract {

    // Define table name and column names
    object IsiListTable : BaseColumns {
        const val TABLE_NAME = "isi_list"
        const val COLUMN_TITLE = "title"
        const val COLUMN_LOGO = "logo"
    }

    // SQL statement for creating the IsiListTable
    const val SQL_CREATE_ISI_LIST_TABLE = """
        CREATE TABLE ${IsiListTable.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${IsiListTable.COLUMN_TITLE} TEXT,
            ${IsiListTable.COLUMN_LOGO} TEXT
        )
    """

    // ... other table definitions and SQL statements ...
}
