package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge
import com.jaketschwartz.adventofcode.extensions.chainedTo

class Challenge202105 : AdventChallenge {
    override val day: Int = 5
    override val year: Int = 2021
    override val challengeName: String = "Hydrothermal Venture"

    override fun partOne(lines: List<String>): Any = lines.map(Line::fromString).filter { it.isStraight }.intersectionsCount()

    override fun partTwo(lines: List<String>): Any = lines.map(Line::fromString).intersectionsCount()

    private data class Coordinate(val x: Int, val y: Int)

    private data class Line(val startCoord: Coordinate, val endCoord: Coordinate) {
        val isStraight: Boolean by lazy { startCoord.x == endCoord.x || startCoord.y == endCoord.y }
        val xRange: IntProgression by lazy { getProgression(startCoord, endCoord) { it.x } }
        val yRange: IntProgression by lazy { getProgression(startCoord, endCoord) { it.y } }
        val expanded: List<Coordinate> by lazy {
            if (isStraight) {
                xRange.flatMap { x -> yRange.map { y -> Coordinate(x, y) } }
            } else {
                xRange.zip(yRange, ::Coordinate)
            }
        }

        private fun getProgression(
            start: Coordinate,
            end: Coordinate,
            extractor: (Coordinate) -> Int
        ): IntProgression = extractor(start).chainedTo(extractor(end)) { derivedStart, derivedEnd ->
            if (derivedStart > derivedEnd) derivedStart downTo derivedEnd else derivedStart..derivedEnd
        }

        companion object {
            fun fromString(input: String): Line = input.split(" -> ").let { (start, end) ->
                Line(startCoord = start.pointsToCoordinate(), endCoord = end.pointsToCoordinate())
            }

            private fun String.pointsToCoordinate(): Coordinate = split(",")
                .let { (x, y) -> Coordinate(x.toInt(), y.toInt()) }
        }
    }

    private fun Collection<Line>.intersectionsCount(): Int = this
        .flatMap { it.expanded }
        .groupBy { it }
        .count { (_, value) -> value.size > 1 }
}