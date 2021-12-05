package com.jaketschwartz.adventofcode.collection

open class AdventMatrix<T: Any>(val matrixFields: List<List<T>>) {
    companion object {
        fun <T: Any> fromLines(
            lines: List<String>,
            splitFn: (String) -> List<String>,
            parseFn: (String) -> T
        ): AdventMatrix<T> = AdventMatrix(matrixFields = lines.map { line -> splitFn(line).map(parseFn) })
    }

    val columns: List<List<T>> by lazy { rotate(RotationType.RIGHT).matrixFields }

    fun rotate(rotationType: RotationType): AdventMatrix<T> = AdventMatrix(
        matrixFields = matrixFields
            .flatMap { it.withIndex() }
            .groupBy { it.index }
            .values
            .map { column -> column.map { it.value } }
            .let { rightRotatedColumns ->
                when(rotationType) {
                    RotationType.RIGHT -> rightRotatedColumns
                    // On a left rotate, just right rotate the values, then flip that result with a reversed(),
                    // then flip the inner values with a reversed as well
                    RotationType.LEFT -> rightRotatedColumns.reversed().map { it.reversed() }
                }
            }
    )

    enum class RotationType {
        RIGHT,
        LEFT
    }
}