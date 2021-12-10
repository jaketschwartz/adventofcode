package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.collection.AdventMatrix
import com.jaketschwartz.adventofcode.collection.AdventMatrixPoint

class Challenge202109 : Challenge {
    override val day: Int = 9
    override val year: Int = 2021
    override val challengeName: String = "Smoke Basin"

    override fun partOne(lines: List<String>) = AdventMatrix.fromLines(
        lines = lines,
        splitFn = { it.chunked(1) },
        parseFn = { it.toInt() },
    ).getLowPoints().sumOf { it.value + 1 }

    override fun partTwo(lines: List<String>) = AdventMatrix.fromLines(
        lines = lines,
        splitFn = { it.chunked(1) },
        parseFn = { it.toInt() },
    ).let { matrix ->
        matrix.getLowPoints()
            .map { lowPoint -> collectBasin(matrix, lowPoint).distinctBy { it.point } }
            .map { it.size + 1 }
    }
        .sortedDescending()
        .take(3)
        .reduce { total, nextValue -> total * nextValue }

    private fun collectBasin(
        matrix: AdventMatrix<Int>,
        currentPoint: AdventMatrixPoint<Int>,
    ): List<AdventMatrixPoint<Int>> = matrix.getAdjacentCardinalPoints(currentPoint).filter {
        it.value > currentPoint.value && it.value < 9
    }.let { validPoints ->
        validPoints + validPoints.flatMap { validPoint ->
            collectBasin(
                matrix = matrix,
                currentPoint = validPoint,
            )
        }
    }

    private fun AdventMatrix<Int>.getLowPoints(): List<AdventMatrixPoint<Int>> = this.matrixPointMap.values.filter { matrixPoint ->
        this.getAdjacentCardinalPoints(matrixPoint.point.x, matrixPoint.point.y).all {
            it.value > matrixPoint.value
        }
    }
}