package com.cuile.cuile.babytime.contract

import com.cuile.cuile.babytime.model.BodyData
import com.cuile.cuile.babytime.model.TaskManager

class BodyDataAddPresenter (val view: BodyDataAddContract.View,
                            val dataManager: TaskManager,
                            override var isDataMissing: Boolean): BodyDataAddContract.Presenter {

    init {
        view.presenter = this
    }

    override fun saveData(bodyData: BodyData) {
    }



}