package com.cuile.cuile.babytime.model.db

class BodyData(val map: MutableMap<String, Any?>) {

    constructor(name: String, height: Float, weight: Float, photo: String, date: Long, other: String): this(HashMap()) {
        this.name = name
        this.height = height
        this.weight = weight
        this.photo = photo
        this.date = date
        this.other = other
    }

    var _id: Long by map
    var name: String by map
    var height: Float by map
    var weight: Float by map
    var photo: String by map
    var date: Long by map
    var other: String by map
}

class EatData(val map: MutableMap<String, Any?>) {

    constructor(
            name: String,
            foodType: Int,
            extraFoodName: String,
            amount: Int,
            amountR: Int,
            time: Long,
            duration: Int,
            durationR: Int,
            other: String): this(HashMap()) {
        this.name = name
        this.foodType = foodType
        this.extraFoodName = extraFoodName
        this.amount = amount
        this.amountR = amountR
        this.time = time
        this.duration = duration
        this.durationR = durationR
        this.other = other
    }

    var _id: Long by map
    var name: String by map
    var foodType: Int by map
    var extraFoodName: String by map
    var amount: Int by map
    var amountR: Int by map
    var time: Long by map
    var duration: Int by map
    var durationR: Int by map
    var other: String by map
}

class ExcretionData(val map: MutableMap<String, Any?>) {
    constructor(name: String,
                type: Int,
                color: String,
                wetAmount: Int,
                driedAmount: Int,
                wetStatus: Int,
                driedStatus: Int,
                time: Long,
                other: String): this(HashMap()) {
        this.name = name
        this.type = type
        this.color = color
        this.wetAmount = wetAmount
        this.driedAmount = driedAmount
        this.wetStatus = wetStatus
        this.driedStatus = driedStatus
        this.time = time
        this.other = other
    }

    var _id: Long by map
    var name: String by map
    var type: Int by map

    /** 便便颜色 */
    var color: String by map
    var wetAmount: Int by map
    var driedAmount: Int by map
    var wetStatus: Int by map
    var driedStatus: Int by map
    var time: Long by map
    var other: String by map
}

class SleepData(val map: MutableMap<String, Any?>) {

    constructor(name: String, time: Long, duration: Int, quality: Int, other: String): this(HashMap()) {
        this.name = name
        this.time = time
        this.duration = duration
        this.quality = quality
        this.other = other
    }

    var _id: Long by map
    var name: String by map
    var time: Long by map
    var duration: Int by map
    var quality: Int by map
    var other: String by map
}