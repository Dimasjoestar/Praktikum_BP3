package com.pab.ecoscanlite.model

/**
 * Data class untuk menyimpan informasi limbah
 * Digunakan untuk menampilkan data di RecyclerView dan DetailActivity
 */
data class Limbah(
    val id: Int,
    val nama: String,
    val imageResId: Int,
    val deskripsi: String,
    val tips: String
)
