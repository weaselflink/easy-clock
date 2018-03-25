package de.bissell.easyclock;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class Settings {

    public int x = 0;
    public int y = 0;
    public int fontSize = 60;

    public static Settings loadSettings() {
        Settings settings = new Settings();
        if (!getSettingsFile().exists()) {
            return settings;
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(getSettingsFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings.x = Integer.parseInt(properties.getProperty("x"));
        settings.y = Integer.parseInt(properties.getProperty("y"));
        settings.fontSize = Integer.parseInt(properties.getProperty("fontSize"));
        return settings;
    }

    public void save() {
        Properties properties = new Properties();
        properties.setProperty("fontSize", Integer.toString(fontSize));
        properties.setProperty("x", Integer.toString(x));
        properties.setProperty("y", Integer.toString(y));
        try {
            Files.createDirectories(getUserDataDirectory().toPath());
            properties.store(new FileWriter(getSettingsFile()), "position and font size of clock");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File getSettingsFile() {
        return new File(getUserDataDirectory(), "settings.txt");
    }

    private static File getUserDataDirectory() {
        return new File(System.getProperty("user.home") + File.separator + "easyclock" + File.separator);
    }
}
