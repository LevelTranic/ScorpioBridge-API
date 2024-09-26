package one.tranic.scorpio.api.util

/**
 * Utility object for runtime-related functions in the Scorpio API.
 * This object provides methods to retrieve information about the Java runtime environment.
 */
object Runtime {
    private var javaVersion: Int? = null

    /**
     * Retrieves the major version of the Java Runtime Environment.
     *
     * The version is returned as an integer. For example, for Java 8, it returns 8;
     * for Java 11, it returns 11.
     *
     * @return the major Java version as an Int
     */
    fun getJavaVersion(): Int {
        if (javaVersion != null) {
            return javaVersion!!
        }
        var version = System.getProperty("java.version")
        if (version.startsWith("1.")) {
            version = version.substring(2, 3)
        } else {
            val dot = version.indexOf(".")
            if (dot != -1) {
                version = version.substring(0, dot)
            }
        }
        javaVersion = version.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toInt()
        return javaVersion!!
    }
}
