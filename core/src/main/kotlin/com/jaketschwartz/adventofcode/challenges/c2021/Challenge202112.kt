package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge
import com.jaketschwartz.adventofcode.challenges.c2021.Challenge202112.Cave.Companion.START_CAVE
import com.jaketschwartz.adventofcode.extensions.getOrThrow

class Challenge202112 : AdventChallenge {
    override val day: Int = 12
    override val year: Int = 2021
    override val challengeName: String = "Passage Pathing"

    override fun partOne(lines: List<String>) = crawlCaves(Cave.fromLines(lines).getOrThrow(START_CAVE)) { cave, previousPath ->
        cave.isBig || !previousPath.contains(cave)
    }.count { it.any { it.isSmall } }

    override fun partTwo(lines: List<String>) = crawlCaves(Cave.fromLines(lines).getOrThrow(START_CAVE)) { cave, previousPath ->
        cave.isBig ||
                previousPath.none { it.name == cave.name } ||
                previousPath.filter { it.isSmall }.groupBy { it.name }.none { it.value.size > 1 }
    }.size

    private fun crawlCaves(
        startCave: Cave,
        previousPath: List<Cave> = listOf(startCave),
        allowCaveCriteria: (cave: Cave, previousPath: List<Cave>) -> Boolean,
    ): List<List<Cave>> =
        startCave.connectedCaves
            .filterNot { it.isStart }
            .filter { cave -> allowCaveCriteria(cave, previousPath) }
            .map { connectedCave ->
                if (connectedCave.isEnd) {
                    listOf(previousPath + connectedCave)
                } else {
                    crawlCaves(
                        startCave = connectedCave,
                        previousPath = previousPath + connectedCave,
                        allowCaveCriteria = allowCaveCriteria,
                    )
                }
            }
            .flatten()

    private data class Cave(
        val name: String,
        val isStart: Boolean,
        val isEnd: Boolean,
        val connectedCaves: MutableList<Cave> = mutableListOf(),
    ) {
        val isBig: Boolean by lazy { name.first().isUpperCase() && !isStart && !isEnd }
        val isSmall: Boolean by lazy { name.first().isLowerCase() && !isStart && !isEnd }

        companion object {
            const val START_CAVE = "start"
            const val END_CAVE = "end"

            fun fromLines(lines: List<String>): Map<String, Cave> {
                val caves = mutableMapOf<String, Cave>()
                fun getOrCreateCave(caveName: String): Cave = caves[caveName] ?: Cave(
                    name = caveName,
                    isStart = caveName == START_CAVE,
                    isEnd = caveName == END_CAVE,
                ).also { caves[caveName] = it }
                lines.map { it.split("-") }.forEach { (firstValue, secondValue) ->
                    val firstCave = getOrCreateCave(firstValue)
                    val secondCave = getOrCreateCave(secondValue)
                    if (firstCave.connectedCaves.none { it.name == secondCave.name }) {
                        firstCave.connectedCaves.add(secondCave)
                    }
                    if (secondCave.connectedCaves.none { it.name == firstCave.name }) {
                        secondCave.connectedCaves.add(firstCave)
                    }
                }
                return caves
            }
        }

        override fun toString() = "Cave: $name | ${connectedCaves.map { it.name }}"
    }
}