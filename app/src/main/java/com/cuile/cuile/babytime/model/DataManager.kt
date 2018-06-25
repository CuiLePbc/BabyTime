package com.cuile.cuile.babytime.model

import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.model.db.EatData
import com.cuile.cuile.babytime.model.db.ExcretionData
import com.cuile.cuile.babytime.model.db.SleepData

@Suppress("unused")
class DataManager(private val dataSourceInterface: DataSourceInterface) {

    fun getBodyDatasByTimeRange(from: Long, to: Long) = dataSourceInterface.requestBodyDataByDateRange(from, to)

    fun getEatDatasByTimeRange(from: Long, to: Long) = dataSourceInterface.requestEatDataByTimeRange(from, to)

    fun getExcretionDatasByTimeRange(from: Long, to: Long) = dataSourceInterface.requestExcretionDataByTimeRange(from, to)

    fun getSleepDatasByTimeRange(from: Long, to: Long) = dataSourceInterface.requestSleepDataByTimeRange(from, to)

    fun saveBodyData(bodyData: BodyData) = dataSourceInterface.saveBodyData(bodyData)

    fun saveEatData(eatData: EatData) = dataSourceInterface.saveEatData(eatData)

    fun saveExcretionData(excretionData: ExcretionData) = dataSourceInterface.saveExcretionData(excretionData)

    fun saveSleepData(sleepData: SleepData) = dataSourceInterface.saveSleepData(sleepData)

    fun deleteBodyDataById(id: Long) = dataSourceInterface.deleteBodyDataById(id)

    fun deleteEatDataById(id: Long) = dataSourceInterface.deleteEatDataById(id)

    fun deleteExcretionDataById(id: Long) = dataSourceInterface.deleteExcretionDataById(id)

    fun deleteSleepDataById(id: Long) = dataSourceInterface.deleteSleepDataById(id)

    fun deleteAllDatas() = dataSourceInterface.clearAllDatas()

}