package com.jaketschwartz.adventofcode.extensions

/**
 * Left shifts the bits of 1 to by the length of the input bits to produce a bit set like: 10000... where the
 * zero amount is equivalent to the bit length of the initial value.  The subtracts one, to create a mask of a
 * maxed set of bits equivalent to the size of the original bits, ex: 1111.
 * When the original value is applied against this mask with an xor bitwise operator, the negated bits are returned.
 *
 * Sample input: 101001
 * Sample output: 010110
 */
fun Int.bitwiseNegate(bitLength: Int = this.toString(2).length): Int = (1 shl bitLength)
    .let { oneBeyondMask -> this xor (oneBeyondMask - 1) }
