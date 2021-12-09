package com.jaketschwartz.adventofcode.extensions

import kotlin.reflect.KClass

fun <T: Any> T?.checkNotNull(lazyMessage: () -> String): T {
    checkNotNull(this, lazyMessage)
    return this
}

fun <T: Any> KClass<T>.qualifiedNameNotNull(): String = this.qualifiedName
    .checkNotNull { "Expected the qualified name to be set" }

fun <T: Any> T?.ifNull(block: () -> Unit): T? = this.also { if (this == null) block() }

fun <T: Any, U: Any, V: Any> T.chainedTo(other: U, action: (T, U) -> V): V = action(this, other)

fun <T: Any> T?.orDefault(block: () -> T): T = this ?: block()

fun <T: Any> T?.orDefault(other: T): T = this ?: other