package com.cuile.cuile.babytime.model.db

object BodyDataTable {
    const val TABLE = "BodyData"
    const val ID = "_id"
    const val NAME = "name"
    const val HEIGHT = "height"
    const val WEIGHT = "weight"
    const val PHOTO = "photo"
    const val DATE = "date"
    const val OTHER = "other"
}

object EatDataTable {

    const val TABLE = "EatData"
    const val ID = "_id"
    const val NAME = "name"
    const val FOOD_TYPE = "foodType"
    const val EXTRA_FOOD_NAME = "extraFoodName"
    const val AMOUNT = "amount"
    const val AMOUNT_R = "amountR"
    const val TIME = "time"
    const val DURATION = "duration"
    const val DURATION_R = "durationR"
    const val OTHER = "other"
}

object ExcretionDataTable {
    const val TABLE = "ExcretionData"
    const val ID = "_id"
    const val NAME = "name"
    const val TYPE = "type"
    const val COLOR = "color"
    const val WET_AMOUNT = "wetAmount"
    const val DRY_AMOUNT = "driedAmount"
    const val WET_STATUS = "wetStatus"
    const val DRY_STATUS = "driedStatus"
    const val TIME = "time"
    const val OTHER = "other"
}

object SleepDataTable {
    const val TABLE = "SleepData"
    const val ID = "_id"
    const val NAME = "name"
    const val TIME = "time"
    const val DURATION = "duration"
    const val QUALITY = "quality"
    const val OTHER = "other"
}