package com.jaketschwartz.adventofcode.collection

open class AdventMatrix<T: Any>(
    val matrixFields: List<List<T>>,
) {
    companion object {
        fun <T: Any> fromLines(
            lines: List<String>,
            splitFn: (String) -> List<String>,
            parseFn: (String) -> T
        ): AdventMatrix<T> = AdventMatrix(matrixFields = lines.map { line -> splitFn(line).map(parseFn) })
    }

    val matrixPointMap: Map<AdventPoint, AdventMatrixPoint<T>> by lazy {
        matrixFields
            .flatMapIndexed { xIndex, yList ->
                yList.mapIndexed { yIndex, value ->
                    AdventMatrixPoint(
                        point = AdventPoint(x = xIndex, y = yIndex),
                        value = value,
                    )
                }
            }.associateBy { it.point }
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

    fun getPointOrNull(x: Int, y: Int): AdventMatrixPoint<T>? = matrixPointMap[x pointTo y]

    fun getAdjacentCardinalPoints(x: Int, y: Int): List<AdventMatrixPoint<T>> = listOfNotNull(
        // Above
        getPointOrNull(x - 1, y),
        // Left
        getPointOrNull(x, y - 1),
        // Right
        getPointOrNull(x + 1, y),
        // Below
        getPointOrNull(x, y + 1),
    )

    fun getAdjacentPoints(x: Int, y: Int): List<AdventMatrixPoint<T>> = listOfNotNull(
        // Top-left
        getPointOrNull(x - 1, y - 1),
        // Above
        getPointOrNull(x, y - 1),
        // Top-right
        getPointOrNull(x + 1, y - 1),
        // Left
        getPointOrNull(x - 1, y),
        // Right
        getPointOrNull(x + 1, y),
        // Bottom-left
        getPointOrNull(x - 1, y + 1),
        // Below
        getPointOrNull(x, y + 1),
        // Bottom-right
        getPointOrNull(x + 1, y + 1),
    )

    fun getAdjacentCardinalPoints(point: AdventPoint): List<AdventMatrixPoint<T>> = getAdjacentCardinalPoints(x = point.x, y = point.y)
    fun getAdjacentCardinalPoints(pointValue: AdventMatrixPoint<T>): List<AdventMatrixPoint<T>> = getAdjacentCardinalPoints(pointValue.point)

    fun mapValues(valueTransform: (T) -> T): AdventMatrix<T> = AdventMatrix(
        matrixFields = matrixFields.map { fieldRow ->
            fieldRow.map { valueTransform(it) }
        }
    )

    enum class RotationType {
        RIGHT,
        LEFT
    }
}

data class AdventMatrixPoint<T: Any>(
    val point: AdventPoint,
    val value: T,
)