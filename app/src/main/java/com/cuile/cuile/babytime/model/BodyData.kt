package com.cuile.cuile.babytime.model

import android.graphics.Bitmap

class BodyData(val map: MutableMap<String, Any?>) {

    constructor(name: String, height: Float, weight: Float, photo: Bitmap, date: Long, other: String): this(HashMap()) {
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
    var photo: Bitmap by map
    var date: Long by map
    var other: String by map
}