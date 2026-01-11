package com.pab.ecolifehub.models

data class WasteDetail(
    val name: String,
    val description: String,
    val impact: String,
    val tips: List<String>,
    val imageResId: Int
)
