package de.stefanbissell.easyclock

import kotlin.io.path.Path

class Settings(
    var x: Int = 10,
    var y: Int = 10,
    var fontSize: Int = 80
) {

    fun save() {
        userDataDirectory.toFile().mkdirs()
        settingsFile.toFile().writeText(
            """
                # position and font size of clock
                fontSize=$fontSize
                x=$x
                y=$y
            """.trimIndent()
        )
    }

    companion object {
        fun loadSettings(): Settings =
            if (settingsFile.toFile().exists()) {
                loadProperties().let {
                    Settings(
                        x = it.getProperty("x") ?: 10,
                        y = it.getProperty("y") ?: 10,
                        fontSize = it.getProperty("fontSize") ?: 80
                    )
                }
            } else {
                Settings()
            }

        private fun loadProperties() = settingsFile.toFile().readText()

        private fun String.getProperty(name: String): Int? {
            val regex = Regex("$name\\s*=\\s*(\\d+)")
            return regex
                .find(this)
                ?.groups
                ?.get(1)
                ?.value
                ?.toIntOrNull()
        }

        private val userDataDirectory
            get() = Path(System.getProperty("user.home")).resolve("easyclock")
        private val settingsFile
            get() = userDataDirectory.resolve("settings.txt")
    }
}
