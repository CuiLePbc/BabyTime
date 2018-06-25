package com.cuile.cuile.babytime.model.db

import android.database.sqlite.SQLiteDatabase
import com.cuile.cuile.babytime.MyApplication
import org.jetbrains.anko.db.*

class BabyTimeDbHelper: ManagedSQLiteOpenHelper(MyApplication.instance.applicationContext, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "babytime.db"
        const val DB_VERSION = 1
        val INSTANCE: BabyTimeDbHelper by lazy { BabyTimeDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(BodyDataTable.TABLE, true,
                BodyDataTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                BodyDataTable.NAME to TEXT,
                BodyDataTable.HEIGHT to REAL,
                BodyDataTable.WEIGHT to REAL,
                BodyDataTable.PHOTO to TEXT,
                BodyDataTable.DATE to INTEGER,
                BodyDataTable.OTHER to TEXT)

        db?.createTable(EatDataTable.TABLE, true,
                EatDataTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EatDataTable.NAME to TEXT,
                EatDataTable.FOOD_TYPE to INTEGER,
                EatDataTable.EXTRA_FOOD_NAME to TEXT,
                EatDataTable.MULK_ML to INTEGER,
                EatDataTable.NIPPLE_SIDE to INTEGER,
                EatDataTable.TIME to INTEGER,
                EatDataTable.DURATION to INTEGER,
                EatDataTable.OTHER to TEXT)

        db?.createTable(ExcretionDataTable.TABLE, true,
                ExcretionDataTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                ExcretionDataTable.NAME to TEXT,
                ExcretionDataTable.TYPE to INTEGER,
                ExcretionDataTable.COLOR to TEXT,
                ExcretionDataTable.WET_AMOUNT to INTEGER,
                ExcretionDataTable.DRY_AMOUNT to INTEGER,
                ExcretionDataTable.WET_STATUS to INTEGER,
                ExcretionDataTable.DRY_STATUS to INTEGER,
                ExcretionDataTable.TIME to INTEGER,
                ExcretionDataTable.OTHER to TEXT)

        db?.createTable(SleepDataTable.TABLE, true,
                SleepDataTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                SleepDataTable.NAME to TEXT,
                SleepDataTable.TIME to INTEGER,
                SleepDataTable.DURATION to INTEGER,
                SleepDataTable.QUALITY to INTEGER,
                SleepDataTable.OTHER to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db?.dropTable(BodyDataTable.TABLE, true)
            db?.dropTable(EatDataTable.TABLE, true)
            db?.dropTable(ExcretionDataTable.TABLE, true)
            db?.dropTable(SleepDataTable.TABLE, true)
            onCreate(db)
        }
    }
}