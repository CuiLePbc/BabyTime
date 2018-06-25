package com.cuile.cuile.babytime.model.db

class BabyTimeDbDataMapper constructor() {

    fun convertToBodyDatasDomain(bodyDataList: List<BodyData>) = bodyDataList.map { convertToBodyDataDomain(it) }
    private fun convertToBodyDataDomain(bodyData: BodyData) = bodyData

    fun convertFromBodyDataDomain(bodyData: BodyData) = bodyData


    fun convertToEatDatasDomain(eatDatas: List<EatData>) = eatDatas.map { convertToEatDataDomain(it) }
    private fun convertToEatDataDomain(eatData: EatData) = eatData

    fun convertFromEatDataDomain(eatData: EatData) = eatData


    fun convertToExcretionDatasDomain(excretionDatas: List<ExcretionData>) = excretionDatas.map { convertToExcretionDataDomain(it) }
    private fun convertToExcretionDataDomain(excretionData: ExcretionData) = excretionData

    fun convertFromExcretionDataDomain(excretionData: ExcretionData) = excretionData



    fun convertToSleepDatasDomain(sleepDatas: List<SleepData>) = sleepDatas.map { convertToSleepDataDomain(it) }
    private fun convertToSleepDataDomain(sleepData: SleepData) = sleepData

    fun convertFromSleepDataDomain(sleepData: SleepData) = sleepData
}