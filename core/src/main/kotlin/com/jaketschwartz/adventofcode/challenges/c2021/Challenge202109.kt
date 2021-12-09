package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.collection.AdventMatrix
import com.jaketschwartz.adventofcode.collection.AdventMatrixPointValue

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
        val lowPoints = matrix.getLowPoints()
        lowPoints.map { lowPoint ->

        }
    }

    private fun collectBasin(
        matrix: AdventMatrix<Int>,
        currentPoint: AdventMatrixPointValue<Int>,
    ): List<AdventMatrixPointValue<Int>> = matrix.getAdjacentHorizontalPoints(currentPoint).filter {
        it.value > currentPoint.value && it.value < 9
    }.let { validPoints ->
        validPoints + validPoints.flatMap { validPoint ->
            collectBasin(
                matrix = matrix,
                currentPoint = validPoint,
            )
        }
    }

    private fun AdventMatrix<Int>.getLowPoints(): List<AdventMatrixPointValue<Int>> = this.matrixPointMap.values.filter { matrixPoint ->
        this.getAdjacentHorizontalPoints(matrixPoint.point.x, matrixPoint.point.y).all {
            it.value > matrixPoint.value
        }
    }
}