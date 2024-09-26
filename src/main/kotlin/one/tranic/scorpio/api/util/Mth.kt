package one.tranic.scorpio.api.util

object Mth {
    fun isValidNumber(input: String): Boolean {
        return input.toIntOrNull()?.let { it in -1000..1000 } ?: false
    }
}