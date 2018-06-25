package com.cuile.cuile.babytime.vp.body.add

import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.model.DataManager

class BodyDataAddPresenter (val view: BodyDataAddContract.View,
                            val dataManager: DataManager,
                            override var isDataMissing: Boolean): BodyDataAddContract.Presenter {

    init {
        view.presenter = this
    }

    override fun saveData(bodyData: BodyData) {
    }



}