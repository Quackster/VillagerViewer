package org.oldskooler.villagerviewer.config;

import org.oldskooler.villagerviewer.VillagerViewer;

import java.io.*;
import java.util.Properties;

/**
 * Configuration handler for the VillagerViewer mod's villager edit settings.
 * <p>
 * This class manages loading and saving configuration options from a properties file.
 * Currently supports the "requireStick" option which controls if a stick is required.
 * </p>
 */
public class VillagerEditConfig {
    private static final File CONFIG_FILE = new File("config/" + VillagerViewer.MOD_NAME.toLowerCase() + ".properties");
    private final Properties properties = new Properties();

    private static final String REQUIRE_STICK_KEY = "requireStick";
    private static final boolean DEFAULT_REQUIRE_STICK = true;

    public boolean requireStick;

    /**
     * Loads the configuration from the properties file.
     * If the file or the property does not exist, defaults are used.
     * After loading, the configuration is saved back to the file
     * to ensure defaults are persisted.
     *
     * @return a loaded or newly created {@code VillagerEditConfig} instance
     */
    public static VillagerEditConfig load() {
        VillagerEditConfig config = new VillagerEditConfig();

        // Ensure the config directory exists
        File parentDir = CONFIG_FILE.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Load properties from the file if it exists
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config.properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Parse the 'requireStick' property, defaulting if missing or invalid
        String requireStickValue = config.properties.getProperty(REQUIRE_STICK_KEY);

        if (requireStickValue != null) {
            config.requireStick = requireStickValue.trim().equalsIgnoreCase("true");
        } else {
            config.requireStick = DEFAULT_REQUIRE_STICK;
        }

        // Save config to persist any defaults
        config.save();

        return config;
    }

    /**
     * Saves the current configuration to the properties file.
     * Updates the properties object and writes them out.
     */
    public void save() {
        set(REQUIRE_STICK_KEY, Boolean.toString(requireStick));

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            properties.store(writer, VillagerViewer.MOD_NAME + " Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a property key to a specified value in the properties object.
     *
     * @param key   the property key to set
     * @param value the value to associate with the key
     */
    private void set(String key, String value) {
        properties.setProperty(key, value);
    }
}