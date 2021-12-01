package com.jaketschwartz.adventofcode.parser

import com.jaketschwartz.adventofcode.extensions.padSingleDigit
import java.time.LocalDate

/**
 * This simple parser allows you to pull an advent text file based on the day and year provided.
 * All advent of code challenges occur in December, so there is no month input.
 * The files should be placed in a directory called advent-day-files in the resource folder, named as such:
 * year-day.txt.
 * Ex:
 * A challenge on December 7th, 2023 would be in a directory and named as such:
 * advent-day-files/2023-07.txt.
 *
 * Remember kids, always pad your strings with zeroes at the front for added complexity when using numbers below 10.
 */
class AdventFileParser(
    year: Int,
    day: Int,
    targetDirectory: String = DEFAULT_TARGET_DIRECTORY,
    fileExtension: String = DEFAULT_FILE_EXTENSION,
) {
    companion object {
        const val MIN_DAY_ALLOWED: Int = 1
        const val MAX_DAY_ALLOWED: Int = 25
        const val MIN_YEAR_ALLOWED: Int = 2015
        const val DEFAULT_TARGET_DIRECTORY: String = "advent-day-files"
        const val DEFAULT_FILE_EXTENSION: String = ".txt"
    }

    init {
        check(year in MIN_YEAR_ALLOWED..LocalDate.now().year) { "Advent of Code problems are available starting in $MIN_YEAR_ALLOWED until now" }
        check(day in MIN_DAY_ALLOWED..MAX_DAY_ALLOWED) { "Advent of Code only runs from the first of the month until Christmas... Get it together" }
    }

    private val fileName = "$targetDirectory/$year-${day.padSingleDigit()}$fileExtension"

    val lines: List<String> = this::class.java.classLoader.getResource(fileName)?.readText()?.split("\n")
        ?: throw IllegalArgumentException("Unable to locate the fucking file! It should be in the resources folder, titled: [$fileName]")
}
