package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.extensions.getOrThrow
import com.jaketschwartz.adventofcode.extensions.second
import java.util.*

class Challenge202110 : Challenge {
    override val day: Int = 10
    override val year: Int = 2021
    override val challengeName: String = "Syntax Scoring"

    override fun partOne(lines: List<String>) = lines
        .map { it.chunked(1).map { char -> SyntaxCharacter.fromChar(char.first()) } }
        .mapNotNull { findScore(it, seekIllegalScore = true) }
        .sum()

    override fun partTwo(lines: List<String>) = lines
        .map { it.chunked(1).map { char -> SyntaxCharacter.fromChar(char.first()) } }
        .mapNotNull { findScore(it, seekIllegalScore = false) }
        .sorted()
        // Cheatcode: The puzzle says that there will always be an odd number of values at the end, and it requires the
        // middle value.  By sorting and then finding the size / 2, we can always find the middle index because it will
        // end up being a number with a .5 in it, which gets dropped by Int division.
        .let { it[it.size / 2] }

    private fun findScore(sourceList: List<SyntaxCharacter>, seekIllegalScore: Boolean): Long? {
        val runningList = mutableListOf<SyntaxCharacter>()
        sourceList.forEach { character ->
            // Keep track of all openers as we iterate through - this allows us to track when a closer doesn't
            // match an opener
            if (character.isOpening) {
                runningList.add(character)
            } else {
                // Get the most recent opener.  It should match the closer because we're going from the inside out.
                // If it doesn't, then the line is corrupted
                val latestOpener = runningList.last()
                if (character.char != latestOpener.detail.closingChar) {
                    return character.detail.illegalScore.takeIf { seekIllegalScore }
                }
                runningList.removeLast()
            }
        }
        return if (seekIllegalScore) {
            null
        } else {
            // The list of all values to calculate needs to be reversed because the characters that complete the
            // lines will be added in reverse order
            runningList.reversed().fold(0L) { total, currentChar ->
                total.times(5).plus(currentChar.detail.incompleteScore)
            }
        }
    }

    private data class SyntaxCharacter(
        val uuid: UUID = UUID.randomUUID(),
        val char: Char,
        val detail: SyntaxDetail,
        var matchingChar: SyntaxCharacter? = null,
    ) {
        val isOpening: Boolean by lazy { char == detail.openingChar }
        val isClosing: Boolean by lazy { char == detail.closingChar }

        companion object {
            fun fromChar(char: Char): SyntaxCharacter = SyntaxCharacter(
                char = char,
                detail = SyntaxDetail.ALL_CHAR_MAP.getOrThrow(char),
            )
        }
    }

    private enum class SyntaxDetail(
        val openingChar: Char,
        val closingChar: Char,
        val illegalScore: Long,
        val incompleteScore: Long,
    ) {
        PARENTHESIS(
            openingChar = '(',
            closingChar = ')',
            illegalScore = 3,
            incompleteScore = 1,
        ),
        SQUARE_BRACE(
            openingChar = '[',
            closingChar = ']',
            illegalScore = 57,
            incompleteScore = 2,
        ),
        CURLY_BRACE(
            openingChar = '{',
            closingChar = '}',
            illegalScore = 1197,
            incompleteScore = 3,
        ),
        ANGLE_BRACE(
            openingChar = '<',
            closingChar = '>',
            illegalScore = 25137,
            incompleteScore = 4,
        );

        companion object {
            val OPENING_CHAR_MAP by lazy { values().associateBy { it.openingChar } }
            val CLOSING_CHAR_MAP by lazy { values().associateBy { it.closingChar } }
            val ALL_CHAR_MAP by lazy { OPENING_CHAR_MAP + CLOSING_CHAR_MAP }
        }
    }
}