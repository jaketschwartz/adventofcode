package com.jaketschwartz.adventofcode.parser

import com.jaketschwartz.adventofcode.extensions.secondOrNull
import com.jaketschwartz.adventofcode.extensions.thirdOrNull
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParserTest {
    @Test
    fun testIllegalYearInput() {
        assertFails("Using too low a value for the fucking year should cause an exception") {
            AdventFileParser(year = AdventFileParser.MIN_YEAR_ALLOWED - 1, day = AdventFileParser.MIN_DAY_ALLOWED)
        }
        assertFails("Using too high a value for the damn year should cause an exception") {
            AdventFileParser(year = LocalDate.now().year + 1, day = AdventFileParser.MIN_DAY_ALLOWED)
        }
    }

    @Test
    fun testIllegalDayInput() {
        assertFails("Using too low a value for the bullshitting day should cause an exception.  FUCK") {
            AdventFileParser(year = AdventFileParser.MIN_YEAR_ALLOWED, day = AdventFileParser.MIN_DAY_ALLOWED - 1)
        }
        assertFails("Using too high a value for the plopping day should cause an exception") {
            AdventFileParser(year = AdventFileParser.MIN_YEAR_ALLOWED, day = AdventFileParser.MAX_DAY_ALLOWED + 1)
        }
    }

    @Test
    fun testLoadSampleFile() {
        // Targets sample file in test resources directory: advent-unit-test-files/2018-05.txt
        val lines = AdventFileParser(year = 2018, day = 5, targetDirectory = "advent-unit-test-files").lines
        assertEquals(
            expected = "First Line",
            actual = lines.firstOrNull(),
            message = "Expected the first line to be parsed properly",
        )
        assertEquals(
            expected = "Second Line",
            actual = lines.secondOrNull(),
            message = "Expected the second line to be parsed properly",
        )
        assertEquals(
            expected = "Fuck You",
            actual = lines.thirdOrNull(),
            message = "Expected the third line to be parsed properly",
        )
    }
}
