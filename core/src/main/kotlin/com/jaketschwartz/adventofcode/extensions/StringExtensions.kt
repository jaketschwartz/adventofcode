package com.jaketschwartz.adventofcode.extensions

inline fun <reified T: Enum<T>> String.toEnum(): T = enumValueOf(this)

fun Int.padSingleDigit(): String = toString().padStart(2, '0')
