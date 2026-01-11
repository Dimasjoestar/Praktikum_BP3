package com.pab.ecolifehub.models

data class DailyHabit(
    val id: Int,
    val title: String,
    var isCompleted: Boolean = false
)
