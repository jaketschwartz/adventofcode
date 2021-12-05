package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge

class Challenge202102 : Challenge {
    override val day: Int = 2
    override val year: Int = 2021
    override val challengeName: String = "Depth Movement"

    override fun partOne(lines: List<String>): Int = lines
        .fold(PositionStats()) { positionStats, currentLine ->
            currentLine.split(" ").let { (directive, value) ->
                positionStats.copy(
                    horizontal = if (directive == "forward") positionStats.horizontal + value.toInt() else positionStats.horizontal,
                    depth = when (directive) {
                        "up" -> positionStats.depth - value.toInt()
                        "down" -> positionStats.depth + value.toInt()
                        else -> positionStats.depth
                    }
                )
            }
        }
        .let { stats -> stats.depth * stats.horizontal }

    override fun partTwo(lines: List<String>): Int = lines
        .fold(PositionStatsV2()) { positionStats, currentLine ->
            val (directive, value) = currentLine.split(" ").let { (directive, value) -> directive to value.toInt() }
            when(directive) {
                "up" -> PositionStatsV2(aim = -value)
                "down" -> PositionStatsV2(aim = value)
                "forward" -> PositionStatsV2(horizontal = value, depth = positionStats.aim * value)
                else -> throw IllegalStateException("Illegal directive: $directive")
            }.let { result ->
                positionStats.copy(
                    aim = result.aim + positionStats.aim,
                    horizontal = result.horizontal + positionStats.horizontal,
                    depth = result.depth + positionStats.depth,
                )
            }
        }
        .let { stats -> stats.horizontal * stats.depth }

    private data class PositionStats(val horizontal: Int = 0, val depth: Int = 0)

    private data class PositionStatsV2(val aim: Int = 0, val horizontal: Int = 0, val depth: Int = 0)
}
