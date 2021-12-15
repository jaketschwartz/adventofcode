package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge
import com.jaketschwartz.adventofcode.extensions.getOrThrow

class Challenge202114 : AdventChallenge {
    override val day: Int = 14
    override val year: Int = 2021
    override val challengeName: String = "Extended Polymerization"

    override fun partOne(lines: List<String>) = PolymerChallengeDriver
        .fromLines(lines)
        .transformTimes(10)
        .getMinMaxSolution()

    override fun partTwo(lines: List<String>) = PolymerChallengeDriver
        .fromLines(lines)
        .transformTimes(40)
        .getMinMaxSolution()

    private class PolymerChallengeDriver(
        private val initialValue: String,
        private val patternMap: Map<String, SplitMonitor>,
    ) {
        private var currentValueCounts: Map<String, Long> = initialValue
            .windowed(size = 2, step = 1)
            .let { initialPatternList ->
                initialPatternList.associateWith { pattern ->
                    initialPatternList.count { it == pattern }.toLong()
                }
            }

        fun getMinMaxSolution(): Long = currentValueCounts.keys.joinToString("").chunked(1).distinct().let { distinctCharacters ->
            distinctCharacters.associateWith { charString ->
                currentValueCounts.keys.filter { it.endsWith(charString) }.sumOf { relevantKey -> currentValueCounts.getOrDefault(relevantKey, 0) }
            }
        }.let { charCountMap -> charCountMap.entries.maxOf { it.value } - charCountMap.entries.minOf { it.value } }

        fun transformTimes(times: Int): PolymerChallengeDriver = apply {
            if (times > 0) repeat(times) {
                transform()
            }
        }

        fun transform(): PolymerChallengeDriver = apply {
            val newValueCount = currentValueCounts.toMutableMap()
            patternMap.forEach { (key, split) ->
                currentValueCounts[key]?.also { existingValueCount ->
                    newValueCount[split.firstSplit] = newValueCount.getOrDefault(split.firstSplit, 0) + existingValueCount
                    newValueCount[split.secondSplit] = newValueCount.getOrDefault(split.secondSplit, 0) + existingValueCount
                    newValueCount[key] = newValueCount.getOrThrow(key) - existingValueCount
                    if (newValueCount.getOrThrow(key) <= 0) {
                        newValueCount.remove(key)
                    }
                }
            }
            currentValueCounts = newValueCount
        }

        companion object {
            fun fromLines(lines: List<String>): PolymerChallengeDriver = PolymerChallengeDriver(
                initialValue = lines.first(),
                patternMap = lines.subList(2, lines.size)
                    .map { line -> line.split(" -> ") }
                    .associate { (pattern, insertString) -> pattern to ("${pattern[0]}$insertString" splitMonitor "$insertString${pattern[1]}") }
            )
            private infix fun String.splitMonitor(other: String) = SplitMonitor(this, other)
        }

        private data class SplitMonitor(val firstSplit: String, val secondSplit: String)
    }
}