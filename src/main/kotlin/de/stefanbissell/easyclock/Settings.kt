package de.stefanbissell.easyclock

import java.util.Properties
import java.nio.file.Files
import java.io.FileWriter
import java.io.FileReader
import java.io.File

class Settings(
    var x: Int = 10,
    var y: Int = 10,
    var fontSize: Int = 60
) {

    fun save() {
        val properties = Properties().apply {
            setProperty("fontSize", fontSize.toString())
            setProperty("x", x.toString())
            setProperty("y", y.toString())
        }
        Files.createDirectories(userDataDirectory.toPath())
        properties.store(FileWriter(settingsFile), "position and font size of clock")
    }

    companion object {
        fun loadSettings(): Settings =
            if (settingsFile.exists()) {
                loadProperties().let {
                    Settings(
                        x = it.getProperty("x").toInt(),
                        y = it.getProperty("y").toInt(),
                        fontSize = it.getProperty("fontSize").toInt()
                    )
                }
            } else {
                Settings()
            }

        private fun loadProperties() =
            Properties().apply {
                load(FileReader(settingsFile))
            }

        private val settingsFile: File
            get() = File(userDataDirectory, "settings.txt")
        private val userDataDirectory: File
            get() = File(System.getProperty("user.home") + File.separator + "easyclock" + File.separator)
    }
}
