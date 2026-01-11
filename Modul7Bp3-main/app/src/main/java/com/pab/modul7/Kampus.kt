package com.pab.modul7

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kampus(
    val name: String,
    val lokasi: String,
    val sejarah: String,
    val photo: Int
) : Parcelable