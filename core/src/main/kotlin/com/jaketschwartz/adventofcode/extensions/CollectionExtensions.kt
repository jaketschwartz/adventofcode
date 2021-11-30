package com.jaketschwartz.adventofcode.extensions

/**
 * Stupid list extension functions to get more elements at expected indices via named reference.
 */
fun <T: Any> List<T>.second(): T = this[1]
fun <T: Any> List<T>.secondOrNull(): T? = this.getOrNull(1)
fun <T: Any> List<T>.third(): T = this[2]
fun <T: Any> List<T>.thirdOrNull(): T? = this.getOrNull(2)
fun <T: Any> List<T>.fouth(): T = this[3]
fun <T: Any> List<T>.fourthOrNull(): T? = this.getOrNull(3)
