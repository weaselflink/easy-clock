package de.stefanbissell.easyclock

import java.util.Properties
import java.nio.file.Files
import java.io.FileWriter
import java.io.IOException
import java.io.FileReader
import java.io.File

class Settings(
    var x: Int = 0,
    var y: Int = 0,
    var fontSize: Int = 60
) {

    fun save() {
        val properties = Properties().apply {
            setProperty("fontSize", fontSize.toString())
            setProperty("x", x.toString())
            setProperty("y", y.toString())
        }
        try {
            Files.createDirectories(userDataDirectory.toPath())
            properties.store(FileWriter(settingsFile), "position and font size of clock")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun loadSettings(): Settings {
            if (!settingsFile.exists()) {
                return Settings()
            }
            val properties = Properties()
            try {
                properties.load(FileReader(settingsFile))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return Settings(
                x = properties.getProperty("x").toInt(),
                y = properties.getProperty("y").toInt(),
                fontSize = properties.getProperty("fontSize").toInt()
            )
        }

        private val settingsFile: File
            get() = File(userDataDirectory, "settings.txt")
        private val userDataDirectory: File
            get() = File(System.getProperty("user.home") + File.separator + "easyclock" + File.separator)
    }
}
