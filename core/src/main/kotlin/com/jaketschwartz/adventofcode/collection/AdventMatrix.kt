package com.jaketschwartz.adventofcode.collection

open class AdventMatrix<T: Any>(
    val matrixFields: List<List<T>>,
) {
    val matrixPointMap: Map<AdventMatrixPoint, AdventMatrixPointValue<T>> = matrixFields
        .flatMapIndexed { xIndex, yList ->
            yList.mapIndexed { yIndex, value ->
                AdventMatrixPointValue(
                    point = AdventMatrixPoint(x = xIndex, y = yIndex),
                    value = value,
                )
            }
        }.associateBy { it.point }

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

    fun getPointOrNull(x: Int, y: Int): AdventMatrixPointValue<T>? = matrixPointMap[x pointTo y]

    fun getAdjacentHorizontalPoints(x: Int, y: Int): List<AdventMatrixPointValue<T>> = listOfNotNull(
        // Above
        getPointOrNull(x - 1, y),
        // Left
        getPointOrNull(x, y - 1),
        // Right
        getPointOrNull(x + 1, y),
        // Below
        getPointOrNull(x, y + 1),
    )

    fun getAdjacentHorizontalPoints(point: AdventMatrixPoint): List<AdventMatrixPointValue<T>> = getAdjacentHorizontalPoints(x = point.x, y = point.y)
    fun getAdjacentHorizontalPoints(pointValue: AdventMatrixPointValue<T>): List<AdventMatrixPointValue<T>> = getAdjacentHorizontalPoints(pointValue.point)

    enum class RotationType {
        RIGHT,
        LEFT
    }
}

data class AdventMatrixPointValue<T: Any>(
    val point: AdventMatrixPoint,
    val value: T,
)

data class AdventMatrixPoint(
    val x: Int,
    val y: Int,
)

infix fun Int.pointTo(y: Int): AdventMatrixPoint = AdventMatrixPoint(this, y)