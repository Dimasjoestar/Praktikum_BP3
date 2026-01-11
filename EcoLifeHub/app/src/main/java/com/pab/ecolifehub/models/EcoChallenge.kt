package com.pab.ecolifehub.models

data class EcoChallenge(
    val id: Int,
    val title: String,
    val description: String,
    val benefit: String,
    val difficulty: String,
    val iconResId: Int
)
