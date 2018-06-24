package com.cuile.cuile.babytime.model

@Suppress("unused")
class TaskManager(private val taskDataSource: TaskDataSource) {

    fun getBodyDatasByTimeRange(from: Long, to: Long) = taskDataSource.requestBodyDataByDateRange(from, to)

    fun getEatDatasByTimeRange(from: Long, to: Long) = taskDataSource.requestEatDataByTimeRange(from, to)

    fun getExcretionDatasByTimeRange(from: Long, to: Long) = taskDataSource.requestExcretionDataByTimeRange(from, to)

    fun getSleepDatasByTimeRange(from: Long, to: Long) = taskDataSource.requestSleepDataByTimeRange(from, to)

    fun saveBodyData(bodyData: BodyData) = taskDataSource.saveBodyData(bodyData)

    fun saveEatData(eatData: EatData) = taskDataSource.saveEatData(eatData)

    fun saveExcretionData(excretionData: ExcretionData) = taskDataSource.saveExcretionData(excretionData)

    fun saveSleepData(sleepData: SleepData) = taskDataSource.saveSleepData(sleepData)

    fun deleteBodyDataById(id: Long) = taskDataSource.deleteBodyDataById(id)

    fun deleteEatDataById(id: Long) = taskDataSource.deleteEatDataById(id)

    fun deleteExcretionDataById(id: Long) = taskDataSource.deleteExcretionDataById(id)

    fun deleteSleepDataById(id: Long) = taskDataSource.deleteSleepDataById(id)

    fun deleteAllDatas() = taskDataSource.clearAllDatas()

}