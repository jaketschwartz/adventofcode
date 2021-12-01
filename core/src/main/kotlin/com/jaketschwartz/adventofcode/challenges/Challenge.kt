package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.parser.AdventFileParser

interface Challenge<T, U> {
    val year: Int
    val day: Int

    fun partOne(lines: List<String>): String
    fun partTwo(lines: List<String>): String

    val challengeName: String get() = "$year/$day"
    val parser: AdventFileParser get() = AdventFileParser(year = year, day = day)
}
