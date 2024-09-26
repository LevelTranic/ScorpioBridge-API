package one.tranic.scorpio.api.util

object Runtime {
    private var javaVersion: Int? = null

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