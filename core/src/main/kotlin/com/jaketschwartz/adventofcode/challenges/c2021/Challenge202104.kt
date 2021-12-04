package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.extensions.*

class Challenge202104 : Challenge {
    override val day: Int = 4
    override val year: Int = 2021
    override val challengeName: String = "Giant Squid"

    override fun partOne(lines: List<String>): String = BingoParameters.fromInput(lines)
        .processParametersAndDeclareWinner()
        .getScore()
        .toString()

    override fun partTwo(lines: List<String>): String = BingoParameters.fromInput(lines)
        .processParametersAndDeclareLastWinner()
        .getScore()
        .toString()

    private data class BingoParameters(
        val commands: List<Int>,
        val bingoBoards: List<BingoBoard>,
    ) {
        companion object {
            fun fromInput(lines: List<String>): BingoParameters = BingoParameters(
                commands = lines.first().split(",").map { it.toInt() },
                bingoBoards = lines.drop(2).filterNot { it.isBlank() }.windowed(size = 5, step = 5)
                    .map(BingoBoard::fromLines),
            )
        }

        fun processParametersAndDeclareWinner(): BingoBoard {
            commands.forEach { command ->
                bingoBoards.forEach { board ->
                    board.processCommand(command)
                    if (board.isWinner()) {
                        return board
                    }
                }
            }
            throw IllegalStateException("Invalid input.  Cannot find a winning board from these fucking commands")
        }

        fun processParametersAndDeclareLastWinner(): BingoBoard {
            commands.fold(bingoBoards) { bingoBoards, command ->
                bingoBoards.forEach { it.processCommand(command) }
                bingoBoards.filterNot { it.isWinner() }.also {
                    if (it.size == 1) {
                        return it.single().also { finalWinningBoard ->
                            var subsequentWinnerCommandIndex = 0
                            while(!finalWinningBoard.isWinner()) {
                                finalWinningBoard.processCommand(commands[subsequentWinnerCommandIndex])
                                subsequentWinnerCommandIndex ++
                            }
                        }
                    }
                }
            }
            throw IllegalStateException("RIP")
        }
    }

    private data class BingoBoard(
        val b: List<BingoMark>,
        val i: List<BingoMark>,
        val n: List<BingoMark>,
        val g: List<BingoMark>,
        val o: List<BingoMark>,
        private var latestCommand: Int? = null
    ) {
        val allBoards: List<BingoMark>
            get() = b.plus(i).plus(n).plus(g).plus(o)

        val latestCommandNotNull: Int
            get() = latestCommand ?: throw IllegalStateException("No command was ever received. This is straight up impossible, you fuck")

        val bColumn: List<BingoMark>
            get() = listOf(b.first(), i.first(), n.first(), g.first(), o.first())

        val iColumn: List<BingoMark>
            get() = listOf(b.second(), i.second(), n.second(), g.second(), o.second())

        val nColumn: List<BingoMark>
            get() = listOf(b.third(), i.third(), n.third(), g.third(), o.third())

        val gColumn: List<BingoMark>
            get() = listOf(b.fourth(), i.fourth(), n.fourth(), g.fourth(), o.fourth())

        val oColumn: List<BingoMark>
            get() = listOf(b.fifth(), i.fifth(), n.fifth(), g.fifth(), o.fifth())

        fun processCommand(command: Int) {
            latestCommand = command
            allBoards.forEach { mark ->
                if (command == mark.bingoValue) {
                    mark.marked = true
                }
            }
        }

        fun isWinner(): Boolean = b.all { it.marked } ||
                i.all { it.marked } ||
                n.all { it.marked } ||
                g.all { it.marked } ||
                o.all { it.marked } ||
                bColumn.all { it.marked } ||
                iColumn.all { it.marked } ||
                nColumn.all { it.marked } ||
                gColumn.all { it.marked } ||
                oColumn.all { it.marked }

        fun getScore(): Int = allBoards.filterNot { it.marked }.sumOf { it.bingoValue } * latestCommandNotNull

        companion object {
            fun fromLines(lines: List<String>): BingoBoard {
                check(lines.size == 5) { "A standard bingo board must have five lines" }
                return BingoBoard(
                    b = lines.toBingoLine(0),
                    i = lines.toBingoLine(1),
                    n = lines.toBingoLine(2),
                    g = lines.toBingoLine(3),
                    o = lines.toBingoLine(4),
                )
            }

            private fun List<String>.toBingoLine(index: Int): List<BingoMark> = this[index]
                .trim()
                .split("\\s+".toRegex())
                .map { BingoMark(bingoValue = it.toInt()) }
                .also { check(it.size == 5) { "Every bingo line should have five elements" } }
        }
    }

    private data class BingoMark(val bingoValue: Int, var marked: Boolean = false)
}