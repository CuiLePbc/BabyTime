package com.cuile.cuile.babytime.model

import android.database.sqlite.SQLiteDatabase
import com.cuile.cuile.babytime.MyApplication
import org.jetbrains.anko.db.*

class BabyTimeDbHelper: ManagedSQLiteOpenHelper(MyApplication.instance.applicationContext, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "babytime.db"
        val DB_VERSION = 1
        val INSTANCE: BabyTimeDbHelper by lazy { BabyTimeDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(BodyDataTable.TABLE, true,
                BodyDataTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                BodyDataTable.NAME to TEXT,
                BodyDataTable.HEIGHT to REAL,
                BodyDataTable.WEIGHT to REAL,
                BodyDataTable.PHOTO to BLOB,
                BodyDataTable.DATE to INTEGER,
                BodyDataTable.OTHER to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db?.dropTable(BodyDataTable.TABLE, true)
            onCreate(db)
        }
    }
}