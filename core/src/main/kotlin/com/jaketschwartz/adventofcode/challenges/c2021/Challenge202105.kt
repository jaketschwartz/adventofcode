package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.extensions.getOrThrow

class Challenge202105 : Challenge {
    override val day: Int = 5
    override val year: Int = 2021
    override val challengeName: String = "Hydrothermal Venture"

    override fun partOne(lines: List<String>): Any = lines
        .map(Line::fromString)
        .filter { it.isStraight }
        .flatMap { it.expanded }
        .groupBy { it }
        .let { coordinateMap -> coordinateMap.keys.count { key -> coordinateMap.getOrThrow(key).size > 1 } }

    override fun partTwo(lines: List<String>): Any = lines
        .map(Line::fromString)
        .flatMap { it.expanded }
        .groupBy { it }
        .let { coordinateMap -> coordinateMap.keys.count { key -> coordinateMap.getOrThrow(key).size > 1 } }

    private data class Coordinate(val x: Int, val y: Int)

    private data class Line(val startCoord: Coordinate, val endCoord: Coordinate) {
        val isStraight: Boolean by lazy { startCoord.x == endCoord.x || startCoord.y == endCoord.y }
        val xRange: IntRange by lazy { startCoord.x.coerceAtMost(endCoord.x).rangeTo(endCoord.x.coerceAtLeast(startCoord.x)) }
        val yRange: IntRange by lazy { startCoord.y.coerceAtMost(endCoord.y).rangeTo(endCoord.y.coerceAtLeast(startCoord.y)) }
        val expanded: List<Coordinate> by lazy {
            // Only straight lines can use this strategy - diagonal lines passed into this will create a rectangle from their points using this shit
            if (isStraight) {
                xRange.flatMap { x -> yRange.map { y -> Coordinate(x, y) } }
            } else {
                // Otherwise, use the int progressions which detail the real advancement between the coordinates and zip
                // them to create the proper point plots in the correct order
                xRange.reverseIfNecessary(startCoord.x)
                    .zip(yRange.reverseIfNecessary(startCoord.y)) { x, y -> Coordinate(x, y) }
            }
        }

        private fun IntRange.reverseIfNecessary(startCoordValue: Int): IntProgression = if (first != startCoordValue) reversed() else this

        companion object {
            fun fromString(input: String): Line = input.split(" -> ").let { (start, end) ->
                Line(startCoord = start.pointsToCoordinate(), endCoord = end.pointsToCoordinate())
            }

            private fun String.pointsToCoordinate(): Coordinate = split(",")
                .map { it.toInt() }
                .let { (x, y) -> Coordinate(x, y) }
        }
    }
}