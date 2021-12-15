package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge
import com.jaketschwartz.adventofcode.extensions.bitwiseNegate

typealias IndexToValueMap = Map<Int, List<IndexedValue<String>>>

class Challenge202103 : AdventChallenge {
    override val day: Int = 3
    override val year: Int = 2021
    override val challengeName: String = "Binary Diagnostic"

    override fun partOne(lines: List<String>): Int = lines
        .getIndexToValueMap()
        // Map the new column groups into partitions of 1s and 0s
        .map { (_, contents) -> contents.joinToString(separator = "") { it.value }.partition { it == '1' } }
        // Create a new binary string that uses the highest of the counts 1/0 to determine the gamma offset
        .joinToString(separator = "") { (ones, zeroes) -> if (ones.length > zeroes.length) "1" else "0" }
        // Convert the binary string result into an integer, achieving gamma
        .toInt(2)
        // Multiply gamma by its bitwise negated counterpart (epsilon), to get the solution
        // Because the first n values can be zeroes, we have to provide the standard line length to determine true bit length.
        // Kotlin's toInt(2) binary conversion ignores trailing zeroes in the bytes
        .let { gamma -> gamma * (gamma.bitwiseNegate(bitLength = lines.first().length)) }

    override fun partTwo(lines: List<String>): Int = lines
        .getIndexToValueMap()
        .mapValues { (_, values) ->
            values.withIndex().map { (index, indexedValue) -> IndexedValue(index, indexedValue.value) }
        }
        .let { indexToValueMap ->
            val oxygenGeneratorRatingIndex = foldIntoOblivion(indexToValueMap, KeepType.MOST_COMMON)
            val co2ScrubberRatingIndex = foldIntoOblivion(indexToValueMap, KeepType.LEAST_COMMON)
            lines[oxygenGeneratorRatingIndex].toInt(2) * lines[co2ScrubberRatingIndex].toInt(2)
        }

    private fun List<String>.getIndexToValueMap(): IndexToValueMap = this
        // Split each line into a List<Char> for each 1 and 0
        .map { it.chunked(1) }
        // Flatten each list out to its indexed counterpart, essentially rotating the list by 90 degrees
        .flatMap { it.withIndex() }
        // Create a map for the index, logically grouping each 1 or 0 with the other values of its column
        .groupBy { it.index }

    private fun foldIntoOblivion(indexToValueMap: IndexToValueMap, keepType: KeepType): Int {
        return indexToValueMap.keys.fold(indexToValueMap) { indexMap, index ->
            val sourceRowIndices = indexMap[index.minus(1).coerceAtLeast(0)]!!.map { it.index }
            val targets = indexMap[index]!!.filter { it.index in sourceRowIndices }
            val breakItUp = targets.size == 1
            val keepValue = targets.partition { it.value == "1" }.let { (ones, zeroes) ->
                when {
                    ones.size > zeroes.size -> if (keepType == KeepType.MOST_COMMON) 1 else 0
                    zeroes.size > ones.size -> if (keepType == KeepType.MOST_COMMON) 0 else 1
                    else -> keepType.defaultValue
                }
            }
            indexMap.mapValues { (innerIndex, keyValueList) ->
                if (innerIndex == index) {
                    if (breakItUp) {
                        targets
                    } else {
                        targets.filter { it.value == keepValue.toString() }
                    }
                } else {
                    keyValueList
                }
            }
        }
            .let { finalMap -> finalMap.keys.last().let { lastIndex -> finalMap[lastIndex]!!.map { it.index } } }
            .distinct()
            .singleOrNull()
            ?: throw IllegalStateException("Surprisingly, this disgusting code didn't work")
    }

    enum class KeepType(val defaultValue: Int) {
        MOST_COMMON(1),
        LEAST_COMMON(0),
    }
}
