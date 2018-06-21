package com.cuile.cuile.babytime.model

import org.jetbrains.anko.db.select

class BabyTimeDb(val babyTimeDbHelper: BabyTimeDbHelper = BabyTimeDbHelper.INSTANCE,
    val babyTimeDbDataMapper: BabyTimeDbDataMapper = BabyTimeDbDataMapper()) {
    fun requestBodyDataByDateRange(from: Long, to: Long) = babyTimeDbHelper.use {
        val bodyDataRequest = "${BodyDataTable.DATE} between $from and $to"
        val bodyData = select(BodyDataTable.TABLE)
                .whereSimple(bodyDataRequest)
                .parseList{ BodyData(HashMap(it)) }
    }
}