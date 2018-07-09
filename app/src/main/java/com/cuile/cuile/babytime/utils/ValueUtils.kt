package com.cuile.cuile.babytime.utils

/**
 * Created by cuile on 18-6-26.
 *
 */
object ValueUtils {
    object EatValue {
        /** 母乳 */
        const val FOOD_TYPE_BREAST = 0
        /** 奶粉 */
        const val FOOD_TYPE_DRIED = 1
        /** 辅食 */
        const val FOOD_TYPE_OTHER = 2

        /** 左胸 */
        const val NIPPLE_LEFT_SIDE = 0
        /** 右胸 */
        const val NIPPLE_RIGHT_SIDE = 1
    }

    object ExcretionValue {
        /** 嘘嘘 */
        const val TYPE_WET = 0
        /** 便便 */
        const val TYPE_DRIED = 1
        /** 嘘嘘和便便 */
        const val TYPE_WET_AND_DRIED = 2
    }

    object SleepValue {
        const val QUALITY_NO = 0
        const val QUALITY_LESS = 1
        const val QUALITY_OK = 2
        const val QUALITY_BETTER = 3
        const val QUALITY_GOOD = 4
    }

    object ShowTitleValue{
        const val BODY_DATA = "身体数据"
        const val EAT_DATA = "饮食数据"
        const val EXCRETION_DATA = "便便数据"
        const val SLEEP_DATA = "睡觉记录"
    }
}