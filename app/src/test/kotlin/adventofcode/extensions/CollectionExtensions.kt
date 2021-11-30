package adventofcode.extensions

fun <T: Any> List<T>.second(): T = this[1]
fun <T: Any> List<T>.secondOrNull(): T? = this.getOrNull(1)
fun <T: Any> List<T>.third(): T = this[2]
fun <T: Any> List<T>.thirdOrNull(): T? = this.getOrNull(2)
