package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge

class Challenge202106 : AdventChallenge {
    override val day: Int = 6
    override val year: Int = 2021
    override val challengeName: String = "Lanternfish"

    override fun partOne(lines: List<String>): Long = Lanternfish.parseFromLine(lines.single()).let { allFish ->
        (1..80).forEach { allFish.forEach { it.addDay() } }
        allFish.sumOf { it.totalLanternfish() }
    }

    override fun partTwo(lines: List<String>): Long = Lanternfish.parseFromLine(lines.single()).let { allFish ->
        (1..256).forEach { allFish.forEach { it.addDay() } }
        allFish.sumOf { it.totalLanternfish() }
    }

    private data class Lanternfish(private var timer: Int) {
        // Start counting down immediately for offspring produce
        private var offspringManager: MutableMap<Int, Long> = mutableMapOf()

        fun addDay() {
            val newOffspring = offspringManager.getOrZero(0)
            offspringManager[0] = 0
            (1..8).forEach { key ->
                val currentValue = offspringManager.getOrZero(key)
                offspringManager[key] = 0
                offspringManager[key - 1] = currentValue
            }
            // Add new offspring equal to all those ready to give birth
            offspringManager[8] = newOffspring + offspringManager.getOrZero(8)
            // Return each parent to its natural breeding cycle of 7 days
            offspringManager[6] = newOffspring + offspringManager.getOrZero(6)
            timer --
            if (timer == -1) {
                timer = 6
                offspringManager[8] = offspringManager.getOrZero(8) + 1
            }
        }

        fun totalLanternfish(): Long = 1 + offspringManager.values.sum()

        private fun MutableMap<Int, Long>.getOrZero(index: Int): Long = this[index] ?: 0L

        companion object {
            fun parseFromLine(fishCsv: String): List<Lanternfish> = fishCsv
                .split(",")
                .map { Lanternfish(it.toInt()) }
        }
    }
}