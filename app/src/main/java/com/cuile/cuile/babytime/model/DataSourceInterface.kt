package com.cuile.cuile.babytime.model

import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.model.db.EatData
import com.cuile.cuile.babytime.model.db.ExcretionData
import com.cuile.cuile.babytime.model.db.SleepData

interface DataSourceInterface {

    fun requestBodyDataByDateRange(from: Long, to: Long): List<BodyData>?

    fun requestEatDataByTimeRange(from: Long, to: Long): List<EatData>?

    fun requestExcretionDataByTimeRange(from: Long, to: Long): List<ExcretionData>?

    fun requestSleepDataByTimeRange(from: Long, to: Long): List<SleepData>?

    fun saveBodyData(bodyData: BodyData): Long

    fun saveEatData(eatData: EatData): Long

    fun saveExcretionData(excretionData: ExcretionData): Long

    fun saveSleepData(sleepData: SleepData): Long

    fun deleteBodyDataById(id: Long): Int

    fun deleteEatDataById(id: Long): Int

    fun deleteExcretionDataById(id: Long): Int

    fun deleteSleepDataById(id: Long): Int

    fun clearAllDatas()

}