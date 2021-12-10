package com.jaketschwartz.adventofcode.collection

data class AdventPoint(
    val x: Int,
    val y: Int,
)

infix fun Int.pointTo(y: Int): AdventPoint = AdventPoint(this, y)