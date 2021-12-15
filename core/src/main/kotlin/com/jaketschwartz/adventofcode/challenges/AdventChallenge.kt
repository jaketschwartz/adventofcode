package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.parser.AdventFileParser
import com.jaketschwartz.adventofcode.util.ArgumentlessConstructorPromise

interface AdventChallenge : ArgumentlessConstructorPromise {
    val year: Int
    val day: Int

    fun partOne(lines: List<String>): Any
    fun partTwo(lines: List<String>): Any

    val challengeName: String get() = "$year/$day"
    val parser: AdventFileParser get() = AdventFileParser(year = year, day = day)
}
