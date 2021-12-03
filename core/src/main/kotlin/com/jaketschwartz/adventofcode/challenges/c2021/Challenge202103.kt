package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.extensions.bitwiseNegate

class Challenge202103 : Challenge {
    override val day: Int = 3
    override val year: Int = 2021
    override val challengeName: String = "Binary Diagnostic"

    override fun partOne(lines: List<String>): String = lines
        .filter { it.isNotBlank() }
        // Split each line into a List<Char> for each 1 and 0
        .map { it.chunked(1) }
        // Flatten each list out to its indexed counterpart, essentially rotating the list by 90 degrees
        .flatMap { it.withIndex() }
        // Create a map for the index, logically grouping each 1 or 0 with the other values of its column
        .groupBy { it.index }
        // Map the new column groups into partitions of 1s and 0s
        .map { (_, contents) -> contents.joinToString(separator = "") { it.value }.partition { it == '1' } }
        // Create a new binary string that uses the higher of the counts 1/0 to determine the gamma offset
        .joinToString(separator = "") { (ones, zeroes) -> if (ones.length > zeroes.length) "1" else "0" }
        // Convert the binary string result into an integer, achieving gamma
        .toInt(2)
        // Multiply gamma by its bitwise negated counterpart (epsilon), to get the solution
        // Because the first n values can be zeroes, we have to provide the standard line length to determine true bit length.
        // Kotlin's toInt(2) binary conversion ignores trailing zeroes in the bytes
        .let { gamma -> gamma * (gamma.bitwiseNegate(bitLength = lines.first().length)) }
        .toString()

    override fun partTwo(lines: List<String>): String = ""
}
