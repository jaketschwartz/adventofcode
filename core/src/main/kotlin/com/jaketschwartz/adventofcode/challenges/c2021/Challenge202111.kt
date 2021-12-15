package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge
import com.jaketschwartz.adventofcode.collection.AdventMatrix
import com.jaketschwartz.adventofcode.collection.AdventMatrixPoint

class Challenge202111 : AdventChallenge {
    override val day: Int = 11
    override val year: Int = 2021
    override val challengeName: String = "Dumbo Octopus"

    override fun partOne(lines: List<String>) = FlashCounter.fromLines(lines)
        .also { flashCounter -> repeat(100) { runNextEnergyStep(flashCounter) } }
        .flashes

    override fun partTwo(lines: List<String>) = FlashCounter.fromLines(lines)
        .let { flashCounter ->
            var currentIteration = 0
            while(flashCounter.matrix.matrixFields.flatten().any { it.energyLevel != 0 }) {
                currentIteration ++
                runNextEnergyStep(flashCounter)
            }
            currentIteration
        }

    private data class DumboOctopus(var energyLevel: Int, var flashed: Boolean = false) {
        fun reset() {
            energyLevel = 0
            flashed = false
        }
    }

    private fun AdventMatrixPoint<DumboOctopus>.flashIfNecessary(flashCounter: FlashCounter) {
        if (this.value.energyLevel > 9 && !this.value.flashed) {
            flashCounter.flashes ++
            this.value.flashed = true
            flashCounter.matrix.getAdjacentPoints(this.point.x, this.point.y).forEach { adjacentOctopus ->
                adjacentOctopus.value.energyLevel ++
                adjacentOctopus.flashIfNecessary(flashCounter)
            }
        }
    }

    private data class FlashCounter(val matrix: AdventMatrix<DumboOctopus>, var flashes: Int = 0) {
        companion object {
            fun fromLines(lines: List<String>): FlashCounter = AdventMatrix.fromLines(
                lines = lines,
                splitFn = { it.chunked(1) },
                parseFn = { DumboOctopus(it.toInt()) },
            ).let(::FlashCounter)
        }
    }

    private fun runNextEnergyStep(flashCounter: FlashCounter) {
        flashCounter.matrix.matrixPointMap.forEach { (_, octopusPoint) ->
            octopusPoint.value.energyLevel ++
        }
        flashCounter.matrix.matrixPointMap.forEach { (_, octopusPoint) ->
            octopusPoint.flashIfNecessary(flashCounter)
        }
        flashCounter.matrix.matrixPointMap.filter { (_, point) -> point.value.flashed }.forEach { (_, octopusPoint) ->
            octopusPoint.value.reset()
        }
    }
}