package com.cuile.cuile.babytime.model

import android.database.sqlite.SQLiteDatabase
import com.cuile.cuile.babytime.model.db.*
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

@Suppress("unused")
class BabyTimeDbDataSource (private val babyTimeDbHelper: BabyTimeDbHelper = BabyTimeDbHelper(), private val babyTimeDbDataMapper: BabyTimeDbDataMapper = BabyTimeDbDataMapper()) : DataSourceInterface {

    override fun requestBodyDataByDateRange(from: Long, to: Long) = babyTimeDbHelper.use {
        val bodyDataRequest = "${BodyDataTable.DATE} >= ? and ${BodyDataTable.DATE} <= ?"
        val bodyDatas = select(BodyDataTable.TABLE)
                .whereSimple(bodyDataRequest, from.toString(), to.toString())
                .parseList(object : MapRowParser<BodyData>{
                    override fun parseRow(columns: Map<String, Any?>): BodyData {
                        return BodyData(HashMap(columns))
                    }
                })

        if (bodyDatas.isNotEmpty())
            babyTimeDbDataMapper.convertToBodyDatasDomain(bodyDatas)
        else
            null
    }

    override fun requestEatDataByTimeRange(from: Long, to: Long) = babyTimeDbHelper.use {
        val eatDataRequest = "${EatDataTable.TIME} >= ? and ${EatDataTable.TIME} <= ?"
        val eatDatas = select(EatDataTable.TABLE)
                .whereSimple(eatDataRequest, from.toString(), to.toString())
                .parseList(object : MapRowParser<EatData>{
                    override fun parseRow(columns: Map<String, Any?>): EatData {
                        return EatData(HashMap(columns))
                    }
                })
        if (eatDatas.isNotEmpty())
            babyTimeDbDataMapper.convertToEatDatasDomain(eatDatas)
        else
            null
    }

    override fun requestExcretionDataByTimeRange(from: Long, to: Long) = babyTimeDbHelper.use {
        val excretionDataRequest = "${ExcretionDataTable.TIME} >= ? and ${ExcretionDataTable.TIME} <= ?"
        val excretionDatas = select(ExcretionDataTable.TABLE)
                .whereSimple(excretionDataRequest, from.toString(), to.toString())
                .parseList(object : MapRowParser<ExcretionData> {
                    override fun parseRow(columns: Map<String, Any?>): ExcretionData {
                        return ExcretionData(HashMap(columns))
                    }
                })
        if (excretionDatas.isNotEmpty())
            babyTimeDbDataMapper.convertToExcretionDatasDomain(excretionDatas)
        else
            null
    }

    override fun requestSleepDataByTimeRange(from: Long, to: Long) = babyTimeDbHelper.use {
        val sleepDataRequest = "${SleepDataTable.TIME} >= ? and ${SleepDataTable.TIME} <= ?"
        val sleepDatas = select(SleepDataTable.TABLE)
                .whereSimple(sleepDataRequest, from.toString(), to.toString())
                .parseList(object : MapRowParser<SleepData>{
                    override fun parseRow(columns: Map<String, Any?>): SleepData {
                        return SleepData(HashMap(columns))
                    }
                })

        if (sleepDatas.isNotEmpty())
            babyTimeDbDataMapper.convertToSleepDatasDomain(sleepDatas)
        else
            null
    }

    override fun saveBodyData(bodyData: BodyData) = babyTimeDbHelper.use {
        with(babyTimeDbDataMapper.convertFromBodyDataDomain(bodyData)) {
            insert(BodyDataTable.TABLE, *map.toVarargArray())
        }
    }

    override fun saveEatData(eatData: EatData) = babyTimeDbHelper.use {
        with(babyTimeDbDataMapper.convertFromEatDataDomain(eatData)) {
            insert(EatDataTable.TABLE, *map.toVarargArray())
        }
    }

    override fun saveExcretionData(excretionData: ExcretionData) = babyTimeDbHelper.use {
        with(babyTimeDbDataMapper.convertFromExcretionDataDomain(excretionData)) {
            insert(ExcretionDataTable.TABLE, *map.toVarargArray())
        }
    }

    override fun saveSleepData(sleepData: SleepData) = babyTimeDbHelper.use {
        with(babyTimeDbDataMapper.convertFromSleepDataDomain(sleepData)) {
            insert(SleepDataTable.TABLE, *map.toVarargArray())
        }
    }

    override fun deleteBodyDataById(id: Long) = babyTimeDbHelper.use {
        delete(BodyDataTable.TABLE, "${BodyDataTable.ID} = {id}", "id" to id)
    }

    override fun deleteEatDataById(id: Long) = babyTimeDbHelper.use {
        delete(EatDataTable.TABLE, "${EatDataTable.ID} = {id}", "id" to id)
    }

    override fun deleteExcretionDataById(id: Long) = babyTimeDbHelper.use {
        delete(ExcretionDataTable.TABLE, "${ExcretionDataTable.ID} = {id}", "id" to id)
    }

    override fun deleteSleepDataById(id: Long) = babyTimeDbHelper.use {
        delete(SleepDataTable.TABLE, "${SleepDataTable.ID} = {id}", "id" to id)
    }

    override fun clearAllDatas() {
        babyTimeDbHelper.use {
            clear(BodyDataTable.TABLE)
            clear(EatDataTable.TABLE)
            clear(ExcretionDataTable.TABLE)
            clear(SleepDataTable.TABLE)
        }
    }

    private fun SQLiteDatabase.clear(tableName: String) {
        execSQL("delete from $tableName")
    }

    private fun <K, V: Any> MutableMap<K, V?>.toVarargArray(): Array<Pair<K, V?>> =
            map { Pair(it.key, it.value) }.toTypedArray()
}