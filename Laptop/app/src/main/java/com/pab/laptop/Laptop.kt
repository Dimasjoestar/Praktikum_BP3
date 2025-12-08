package com.pab.laptop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Laptop(
    val name: String,
    val description: String,
    val photo: Int,
    val spec_processor: String,
    val spec_ram: String,
    val spec_gpu: String,
    val spec_storage: String,
    val spec_display: String
) : Parcelable