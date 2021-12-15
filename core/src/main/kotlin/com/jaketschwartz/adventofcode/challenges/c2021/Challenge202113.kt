package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.AdventChallenge
import java.util.*

class Challenge202113 : AdventChallenge {
    override val day: Int = 13
    override val year: Int = 2021
    override val challengeName: String = "Transparent Origami"

    override fun partOne(lines: List<String>) = DotInstructions.fromLines(lines)
        .also { dotInstructions -> dotInstructions.processInstruction(dotInstructions.instructions.first()) }
        .dots
        .size

    override fun partTwo(lines: List<String>) = DotInstructions.fromLines(lines)
        .also { dotInstructions -> dotInstructions.processAllInstructions() }
        .printDots()

    private data class Dot(var x: Int, var y: Int, val uuid: UUID = UUID.randomUUID())

    private enum class FoldType {
        X, Y;
        companion object {
            fun fromString(string: String): FoldType = when (string) {
                "x" -> X
                "y" -> Y
                else -> throw IllegalArgumentException("Unexpected fold type: $string")
            }
        }
    }

    private data class Instruction(val foldType: FoldType, val cutoff: Int)

    private data class DotInstructions(val dots: MutableList<Dot>, val instructions: List<Instruction>) {
        fun processInstruction(instruction: Instruction) {
            when (instruction.foldType) {
                FoldType.X -> {
                    dots.filter { it.x > instruction.cutoff }.forEach { dot ->
                        dot.x = instruction.cutoff - (dot.x - instruction.cutoff)
                        removeDotIfDuplicate(dot)
                    }
                }
                FoldType.Y -> {
                    dots.filter { it.y > instruction.cutoff }.forEach { dot ->
                        dot.y = instruction.cutoff - (dot.y - instruction.cutoff)
                        removeDotIfDuplicate(dot)
                    }
                }
            }
        }

        fun processAllInstructions() {
            instructions.forEach(::processInstruction)
        }

        fun printDots(): String {
            val dotList = mutableListOf<String>()
            val highestX = dots.maxOf { it.x }
            val highestY = dots.maxOf { it.y }
            repeat(highestY + 1) {
                dotList.add(".".repeat(highestX + 1))
            }
            dots.forEach { dot ->
                val curLine = dotList[dot.y].toCharArray()
                curLine[dot.x] = '#'
                dotList[dot.y] = String(curLine)
            }
            // Add an extra newline for readability
            return "\n${dotList.joinToString("\n")}"
        }

        private fun removeDotIfDuplicate(dot: Dot) {
            if (dots.any { it.uuid != dot.uuid && it.x == dot.x && it.y == dot.y }) {
                dots.remove(dot)
            }
        }

        companion object {
            fun fromLines(lines: List<String>): DotInstructions {
                val dots = mutableListOf<Dot>()
                val instructions = mutableListOf<Instruction>()
                lines.forEach { line ->
                    when {
                        line.startsWith("fold along") -> line.split("fold along ")
                            .let { (_, instruction) -> instruction.split("=") }
                            .also { (foldType, value) ->
                                instructions.add(
                                    Instruction(
                                        foldType = FoldType.fromString(
                                            foldType
                                        ), cutoff = value.toInt()
                                    )
                                )
                            }
                        line.isNotBlank() -> line.split(",")
                            .also { (x, y) -> dots.add(Dot(x.toInt(), y.toInt())) }
                    }
                }
                return DotInstructions(dots = dots, instructions = instructions)
            }
        }
    }
}