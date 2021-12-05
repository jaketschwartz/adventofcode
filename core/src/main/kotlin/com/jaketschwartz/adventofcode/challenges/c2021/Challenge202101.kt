package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge

class Challenge202101 : Challenge {
    override val day: Int = 1
    override val year: Int = 2021
    override val challengeName: String = "Sonar Distance"

    override fun partOne(lines: List<String>): Int = lines
        .mapNotNull { it.toIntOrNull() }
        .fold(PreviousValueHolder(totalIncreases = 0, previousValue = Int.MAX_VALUE)) { valueHolder, newValue ->
            valueHolder.copy(
                totalIncreases = valueHolder.totalIncreases + if (valueHolder.previousValue < newValue) 1 else 0,
                previousValue = newValue,
            )
        }
        .totalIncreases

    override fun partTwo(lines: List<String>): Long = lines
        .mapNotNull { it.toIntOrNull() }
        .let { distanceList ->
            distanceList.indices.sumOf { index: Int ->
                if (distanceList.sumNextThree(index) < distanceList.sumNextThree(index + 1)) 1L else 0
            }
        }

    private data class PreviousValueHolder(val totalIncreases: Int, val previousValue: Int)

    private fun List<Int>.sumNextThree(index: Int): Int = if (index + 2 > size - 1) Int.MIN_VALUE else slice(index..index+2).sum()
}
