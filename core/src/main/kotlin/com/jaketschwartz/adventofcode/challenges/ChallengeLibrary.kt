package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.extensions.padSingleDigit
import com.jaketschwartz.adventofcode.util.AdventTypeLoader
import mu.KotlinLogging

object ChallengeLibrary {
    private val logger = KotlinLogging.logger {}

    private val challengeMap: Map<ChallengeKey, Challenge> by lazy {
        AdventTypeLoader.initializeAllImplementationsOfType<Challenge>()
            .associateBy { challenge -> ChallengeKey(year = challenge.year, day = challenge.day) }
    }

    fun executeChallenge(year: Int, day: Int, executionType: ChallengeExecutionType) {
        val challenge = challengeMap[ChallengeKey(year, day)]
            ?: throw IllegalStateException("The challenge for $year/$${day.padSingleDigit()} has not yet been coded")

        val firstResult = { logger.info("Result for the first challenge for ${challenge.challengeName}: ${challenge.partOne(challenge.parser.lines)}") }
        val secondResult = { logger.info("Result for the second challenge for ${challenge.challengeName}: ${challenge.partTwo(challenge.parser.lines)}") }
        when (executionType) {
            ChallengeExecutionType.FIRST -> firstResult()
            ChallengeExecutionType.SECOND -> secondResult()
            ChallengeExecutionType.BOTH -> firstResult().also { secondResult() }
        }
    }
}

data class ChallengeKey(val year: Int, val day: Int)

enum class ChallengeExecutionType {
    FIRST,
    SECOND,
    BOTH
}

private infix fun Int.day(day: Int): ChallengeKey = ChallengeKey(year = this, day = day)
