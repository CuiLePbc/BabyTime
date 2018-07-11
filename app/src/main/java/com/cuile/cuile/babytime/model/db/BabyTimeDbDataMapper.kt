package com.cuile.cuile.babytime.model.db

import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.ShowMainItemEntity
import com.cuile.cuile.babytime.utils.ValueUtils
import com.cuile.cuile.babytime.utils.ValueUtils.ShowTitleValue
import com.cuile.cuile.babytime.utils.two
import java.util.*

class BabyTimeDbDataMapper {

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
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
        val timeStr = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE).two()}"

        val content = when(sleepData.quality) {
            ValueUtils.SleepValue.QUALITY_GOOD -> "良好睡眠:${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_BETTER -> "较好睡眠:${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_OK -> "普通睡眠:${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_LESS -> "较差睡眠:${sleepData.duration}秒钟"
            ValueUtils.SleepValue.QUALITY_NO -> "糟糕睡眠:${sleepData.duration}秒钟"
            else -> ""
        }

        val startTime = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE).two()}"
        calendar.timeInMillis += sleepData.duration * 1000
        val endTime = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE).two()}"


        return ShowMainItemEntity(
                id = sleepData._id,
                image = R.drawable.sleepdata,
                stickyName = stickyName,
                title = ShowTitleValue.SLEEP_DATA,
                content = content,
                time = timeStr,
                duration = "$startTime ~ $endTime",
                timeInMillions = sleepData.time)
    }

    private fun convertToShowMainItemFromExcretionData(excretionData: ExcretionData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = excretionData.time
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
        val timeStr = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE).two()}"

        val content = when(excretionData.type) {
            ValueUtils.ExcretionValue.TYPE_WET -> "嘘嘘:${excretionData.wetAmount} 状态:${excretionData.wetStatus}"
            ValueUtils.ExcretionValue.TYPE_DRIED -> "便便:${excretionData.driedAmount} 状态:${excretionData.driedStatus}"
            ValueUtils.ExcretionValue.TYPE_WET_AND_DRIED -> "嘘嘘:${excretionData.wetAmount} 状态:${excretionData.wetStatus}" +
                    " 便便:${excretionData.driedAmount} 状态:${excretionData.driedStatus}"
            else -> ""
        }

        return ShowMainItemEntity(
                id = excretionData._id,
                image = R.drawable.excretiondata,
                stickyName = stickyName,
                title = ShowTitleValue.EXCRETION_DATA,
                content = content,
                time = timeStr,
                duration = "",
                timeInMillions = excretionData.time)
    }

    private fun convertToShowMainItemFromBodyData(bodyData: BodyData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = bodyData.date
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DAY_OF_MONTH)}日"

        return ShowMainItemEntity(
                id = bodyData._id,
                image = R.drawable.bodydata,
                stickyName = stickyName,
                title = ShowTitleValue.BODY_DATA,
                content = "身高:${bodyData.height}cm 体重:${bodyData.weight}kg",
                time = stickyName,
                duration = "",
                timeInMillions = bodyData.date)
    }

    private fun convertToShowMainItemFromEatData(eatData: EatData): ShowMainItemEntity {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = eatData.time
        val stickyName = "${calendar.get(Calendar.YEAR)}年${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
        val startTime = "${calendar.get(Calendar.HOUR_OF_DAY).two()}:${calendar.get(Calendar.MINUTE).two()}"
        calendar.timeInMillis += eatData.duration * 1000
        calendar.timeInMillis += eatData.durationR * 1000


        val durationStr: String
        val content: String

        when(eatData.foodType) {
            ValueUtils.EatValue.FOOD_TYPE_BREAST -> {
                content = "母乳:左侧${eatData.amount} 右侧${eatData.amountR}"
                durationStr = "左侧${eatData.duration}秒 右侧${eatData.durationR}秒"
            }
            ValueUtils.EatValue.FOOD_TYPE_DRIED -> {
                content = "奶粉:${eatData.amount}ml"
                durationStr = "${eatData.duration}秒"
            }
            ValueUtils.EatValue.FOOD_TYPE_OTHER -> {
                content = "${eatData.foodType}: ${eatData.amount}ml"
                durationStr = "${eatData.duration}秒"
            }
            else -> {
                content = ""
                durationStr = ""
            }
        }

        return ShowMainItemEntity(
                id = eatData._id,
                image = R.drawable.eatdata,
                stickyName = stickyName,
                title = ShowTitleValue.EAT_DATA,
                content = content,
                time = startTime,
                duration = durationStr,
                timeInMillions = eatData.time)
    }

}