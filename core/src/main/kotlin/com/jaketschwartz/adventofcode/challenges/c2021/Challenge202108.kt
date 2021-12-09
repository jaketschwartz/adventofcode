package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.extensions.second

class Challenge202108 : Challenge {
    override val day: Int = 8
    override val year: Int = 2021
    override val challengeName: String = "Seven Segment Search"

    override fun partOne(lines: List<String>) = lines
        .flatMap { line -> line.split(" | ").second().split(" ") }
        .filter { it.length in EASILY_SOLVABLE_VALUES }
        .size

    override fun partTwo(lines: List<String>) = lines
        .map { line -> line.split(" | ").flatMap { it.split(" ") } }
        .sumOf { lineValues ->
            // Digest the final four values (each line has 4 values at the end of the line)
            lineValues.takeLast(4).joinToString(separator = "") { lineValue ->
                when (lineValue.length) {
                    // EZMODE: none of these lengths can be more than one number
                    2 -> 1
                    3 -> 7
                    4 -> 4
                    7 -> 8
                    // For both 5 and 6 length strings, you simply remove the lettered segments from the target for all
                    // known values (1, 4, 7) and you end up with a total number if you sum those values. Because each
                    // target has a unique segment placement and the letters are constant in their dynamic locations,
                    // each removed set results in a unique sum.
                    5 -> when (lineValue.getSegmentCheckSum(lineValues)) {
                        // (1 rem = 3) + (4 rem = 2) + (7 rem = 2) = 7 total
                        7 -> 3
                        // (1 rem = 4) + (4 rem = 2) + (7 rem = 3) = 9 total
                        9 -> 5
                        // (1 rem = 4) + (4 rem = 3) + (7 rem = 3) = 10 total
                        10 -> 2
                        else -> throw IllegalStateException("Unsolvable line value by my brainhole: $lineValue")
                    }
                    6 -> when (lineValue.getSegmentCheckSum(lineValues)) {
                        // (1 rem = 4) + (4 rem = 2) + (7 rem = 3) = 9 total
                        9 -> 9
                        // (1 rem = 4) + (4 rem = 3) + (7 rem = 3) = 10 total
                        10 -> 0
                        // (1 rem = 5) + (4 rem = 3) + (7 rem = 4) = 12 total
                        12 -> 6
                        else -> throw IllegalStateException("Unsolvable line value by my brainhole: $lineValue")
                    }
                    else -> throw IllegalStateException("Unexpected line length found for value: $lineValue")
                }.toString()
            }.toLong()
        }

    // I bet this can EASILY be done with Regex but I'm a purist (purely shitty whoaoaoaooa)
    private fun String.removeAllCharacters(characters: String): String = characters
        .chunked(1)
        .fold(this) { result, charString -> result.replace(charString, "") }

    /**
     * Sums up all the segments remaining after removing the letters from the first instance of known characters (1, 4, and 7).
     * Because each number's positions are unique enough, this check sum will be different for all target values.
     * This only works because each line has 1, 4, and 7 codes on it.
     */
    private fun String.getSegmentCheckSum(allLineValues: List<String>): Int =
        this.removeAllCharacters(allLineValues.first { it.length == 2 }).length +
            this.removeAllCharacters(allLineValues.first { it.length == 3 }).length +
            this.removeAllCharacters(allLineValues.first { it.length == 4 }).length

    private companion object {
        private val EASILY_SOLVABLE_VALUES = setOf(2, 3, 4, 7)
    }
}