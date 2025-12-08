package com.dicoding.exam.optionalexam3

// TODO
fun manipulateString(str: String, int: Int): String {
    val textPart = str.filter { !it.isDigit() }
    val numberPart = str.filter { it.isDigit() }

    return if (numberPart.isNotEmpty()) {
        val number = numberPart.toInt()
        val result = number * int
        "$textPart$result"
    } else {
        "$str$int"
    }
}