package com.pab.tugasmod4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val name: String,
    val nim: String,
    val email: String,
    val age: Int,
    val city: String
) : Parcelable
