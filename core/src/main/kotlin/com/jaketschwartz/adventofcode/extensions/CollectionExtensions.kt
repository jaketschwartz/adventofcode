package com.jaketschwartz.adventofcode.extensions

/**
 * Stupid list extension functions to get more elements at expected indices via named reference.
 */
fun <T: Any> List<T>.second(): T = this[1]
fun <T: Any> List<T>.secondOrNull(): T? = this.getOrNull(1)
fun <T: Any> List<T>.third(): T = this[2]
fun <T: Any> List<T>.thirdOrNull(): T? = this.getOrNull(2)
fun <T: Any> List<T>.fourth(): T = this[3]
fun <T: Any> List<T>.fourthOrNull(): T? = this.getOrNull(3)
fun <T: Any> List<T>.fifth(): T = this[4]
fun <T: Any> List<T>.fifthOrNull(): T? = this.getOrNull(4)

fun <T: Any> Array<T>.second(): T = this[1]
fun <T: Any> Array<T>.secondOrNull(): T? = this.getOrNull(1)
fun <T: Any> Array<T>.third(): T = this[2]
fun <T: Any> Array<T>.thirdOrNull(): T? = this.getOrNull(2)
fun <T: Any> Array<T>.fourth(): T = this[3]
fun <T: Any> Array<T>.fourthOrNull(): T? = this.getOrNull(3)
fun <T: Any> Array<T>.fifth(): T = this[4]
fun <T: Any> Array<T>.fifthOrNull(): T? = this.getOrNull(4)

fun <T: Any, U: Any> Map<T, U>.getOrThrow(key: T): U = get(key).checkNotNull { "Expected a key of [$key] to exist" }