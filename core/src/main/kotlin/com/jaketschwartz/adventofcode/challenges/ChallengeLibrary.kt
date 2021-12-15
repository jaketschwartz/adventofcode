package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.extensions.*
import com.jaketschwartz.adventofcode.util.AdventTypeLoader
import mu.KotlinLogging

object ChallengeLibrary {
    private val logger = KotlinLogging.logger {}

    private val challengeMap: Map<ChallengeKey, AdventChallenge> by lazy {
        AdventTypeLoader.initializeAllImplementationsOfType<AdventChallenge>()
            .associateBy { challenge -> ChallengeKey(year = challenge.year, day = challenge.day) }
    }

    fun executeChallenge(request: ChallengeRequest) {
        val challenge = challengeMap[ChallengeKey(request.year, request.day)]
            ?: throw IllegalStateException("The challenge for ${request.year}/${request.day.padSingleDigit()} has not yet been coded")
        val (lines, logPrefix) = when (request.sourceType) {
            ChallengeSourceType.LIVE -> challenge.parser.lines to "[LIVE RUN]"
            ChallengeSourceType.SAMPLE -> challenge.parser.sampleLines to "[SAMPLE RUN]"
        }
        val firstResult = { logger.info("$logPrefix Result for the first challenge for ${challenge.challengeName}: ${challenge.partOne(lines)}") }
        val secondResult = { logger.info("$logPrefix Result for the second challenge for ${challenge.challengeName}: ${challenge.partTwo(lines)}") }
        when (request.executionType) {
            ChallengeExecutionType.FIRST -> firstResult()
            ChallengeExecutionType.SECOND -> secondResult()
            ChallengeExecutionType.BOTH -> firstResult().also { secondResult() }
        }
    }

    fun executeChallenge(args: Array<String>) = executeChallenge(ChallengeRequest.fromArgs(args))

    private data class ChallengeKey(val year: Int, val day: Int)
}

enum class ChallengeSourceType {
    LIVE,
    SAMPLE,
}

enum class ChallengeExecutionType {
    FIRST,
    SECOND,
    BOTH,
}

data class ChallengeRequest(
   val year: Int,
   val day: Int,
   val executionType: ChallengeExecutionType,
   val sourceType: ChallengeSourceType,
) {
    companion object {
        fun fromArgs(args: Array<String>): ChallengeRequest = ChallengeRequest(
            year = args.firstOrNull()?.toInt() ?: throw IllegalArgumentException("First argument should be an int, representing the year"),
            day = args.secondOrNull()?.toInt() ?: throw IllegalArgumentException("Second argument should be an int, representing the day"),
            executionType = args.thirdOrNull()?.toEnum<ChallengeExecutionType>() ?: throw IllegalArgumentException("Third argument should be an execution type. One of: ${ChallengeExecutionType.values().toList()}"),
            sourceType = args.fourthOrNull()?.toEnum<ChallengeSourceType>() ?: ChallengeSourceType.LIVE,
        )
    }
}