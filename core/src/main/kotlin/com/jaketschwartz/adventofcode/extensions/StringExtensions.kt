package com.jaketschwartz.adventofcode.extensions

inline fun <reified T: Enum<T>> String.toEnum(): T = enumValueOf(this)

fun Int.padSingleDigit(): String = toString().padStart(2, '0')

fun String.toCharacterCountMap(): Map<Char, Long> = mutableMapOf<Char, Long>().also { countMap ->
    this.forEach { char ->
        val curValue = (countMap[char] ?: 0L) + 1
        countMap[char] = curValue
    }
}