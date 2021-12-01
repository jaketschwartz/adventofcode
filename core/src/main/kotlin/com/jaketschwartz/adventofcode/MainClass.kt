package com.jaketschwartz.adventofcode

import com.jaketschwartz.adventofcode.challenges.ChallengeExecutionType
import com.jaketschwartz.adventofcode.challenges.ChallengeLibrary
import com.jaketschwartz.adventofcode.extensions.toEnum

class MainClass

fun main(args: Array<String>) {
    val year = args.getOrNull(0)?.toInt() ?: throw IllegalArgumentException("First argument should be a year!")
    val day = args.getOrNull(1)?.toInt() ?: throw IllegalArgumentException("Second argument should be a day!")
    val executionType = args.getOrNull(2)?.toEnum<ChallengeExecutionType>() ?: throw IllegalArgumentException("Third argument should be an execution type: ${ChallengeExecutionType.values().toList()}")
    ChallengeLibrary.executeChallenge(year, day, executionType)
}
