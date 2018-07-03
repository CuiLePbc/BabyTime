package com.cuile.cuile.babytime.model.db

import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import com.cuile.cuile.babytime.utils.ValueUtils
import com.cuile.cuile.babytime.utils.two
import java.util.*

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

    fun convertToShowMainItemEntityList(list: List<Any>?): List<ShowMainItemEntity> {
        val result = mutableListOf<ShowMainItemEntity>()
        list?.forEach {
            when(it){
                is BodyData -> result.add(convertToShowMainItemFromBodyData(it))
                is EatData -> result.add(convertToShowMainItemFromEatData(it))
                is ExcretionData -> result.add(convertToShowMainItemFromExcretionData(it))
                is SleepData -> result.add(convertToShowMainItemFromSleepData(it))
            }
        }
        return result
    }

    private fun convertToShowMainItemFromSleepData(sleepData: SleepData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = sleepData.time
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH)}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
        val timeStr = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        val content = when(sleepData.quality) {
            ValueUtils.SleepValue.QUALITY_GOOD -> "良好睡眠：${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_BETTER -> "较好睡眠：${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_OK -> "普通睡眠：${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_LESS -> "较差睡眠：${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_NO -> "糟糕睡眠：${sleepData.duration}秒钟"
            else -> ""
        }

        return ShowMainItemEntity(
                id = sleepData._id,
                image = R.drawable.sleepdata,
                stickyName = stickyName,
                title = "睡觉记录",
                content = content,
                time = timeStr,
                duration = "")
    }

    private fun convertToShowMainItemFromExcretionData(excretionData: ExcretionData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = excretionData.time
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH)}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
        val timeStr = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        val content = when(excretionData.type) {
            ValueUtils.ExcretionValue.TYPE_WET -> "嘘嘘：${excretionData.wetAmount} 状态：${excretionData.wetStatus}"
            ValueUtils.ExcretionValue.TYPE_DRIED -> "便便:${excretionData.driedAmount} 状态：${excretionData.driedStatus}"
            ValueUtils.ExcretionValue.TYPE_WET_AND_DRIED -> "嘘嘘：${excretionData.wetAmount} 状态：${excretionData.wetStatus}" +
                    "/n便便${excretionData.driedAmount} 状态：${excretionData.driedStatus}"
            else -> ""
        }

        return ShowMainItemEntity(
                id = excretionData._id,
                image = R.drawable.excretiondata,
                stickyName = stickyName,
                title = "便便数据",
                content = content,
                time = timeStr,
                duration = "")
    }

    private fun convertToShowMainItemFromBodyData(bodyData: BodyData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = bodyData.date
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH)}月${calendar.get(Calendar.DAY_OF_MONTH)}日"

        return ShowMainItemEntity(
                id = bodyData._id,
                image = R.drawable.bodydata,
                stickyName = stickyName,
                title = "身体数据",
                content = "身高:${bodyData.height}cm体重:${bodyData.weight}kg",
                time = stickyName,
                duration = "")
    }

    private fun convertToShowMainItemFromEatData(eatData: EatData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = eatData.time
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH)}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
        val startTime = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE)}"
        calendar.timeInMillis += eatData.duration[0] * 1000
        val endTime = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE)}"

        val content = when(eatData.foodType) {
            ValueUtils.EatValue.FOOD_TYPE_BREAST -> {
                val sideStr = if (eatData.nippleSide == ValueUtils.EatValue.NIPPLE_LEFT_SIDE) "左侧" else "右侧"
                val amountStr = eatData.eatMotherAmount[0]
                "母乳: $sideStr $amountStr"
            }
            ValueUtils.EatValue.FOOD_TYPE_DRIED -> {
                "奶粉： ${eatData.milkMl}ml"
            }
            ValueUtils.EatValue.FOOD_TYPE_OTHER -> {
                "${eatData.foodType}: ${eatData.milkMl}ml"
            }
            else -> { "" }
        }

        return ShowMainItemEntity(
                id = eatData._id,
                image = R.drawable.eatdata,
                stickyName = stickyName,
                title = "饮食数据",
                content = content,
                time = "$startTime ~ $endTime",
                duration = "${eatData.duration}秒")
    }

}