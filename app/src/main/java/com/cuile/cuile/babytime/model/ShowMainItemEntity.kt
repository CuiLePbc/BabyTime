package com.cuile.cuile.babytime.model

/**
 * Created by cuile on 18-6-29.
 *
 */
data class ShowMainItemEntity(val id: Long,
                              val image: Int,
                              val stickyName: String,
                              val title: String,
                              val content: String,
                              val time: String,
                              val duration: String,
                              val timeInMillions: Long)