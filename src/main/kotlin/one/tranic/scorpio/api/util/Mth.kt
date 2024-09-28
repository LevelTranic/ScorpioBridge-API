package one.tranic.scorpio.api.util

/**
 * Utility object for mathematical and numerical operations in the Scorpio API.
 * This object provides methods to validate number inputs.
 *
 * @since 1.0.0
 */
object Mth {

    /**
     * Checks if the given input string represents a valid number within the range of -1000 to 1000.
     *
     * @since 1.0.0
     * @param input the string input to be validated
     * @return true if the input is a valid integer within the specified range, false otherwise
     */
    fun isValidNumber(input: String): Boolean {
        return input.toIntOrNull()?.let { it in -1000..1000 } ?: false
    }
}
