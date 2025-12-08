package com.dicoding.exam.exam2

// TODO 1
internal fun calculate(valueA: Int, valueB: Int, valueC: Int?): Int {
    val c = valueC ?: 50
    return valueA + valueB - c
}

// TODO 2
internal fun result(result: Int): String {
    return "Result is $result"
}