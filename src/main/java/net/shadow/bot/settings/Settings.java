package net.shadow.bot.settings;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor @Getter
public class Settings {

    private final Logger logger;
    private final Config config;

    public Settings() {
        // Create logger
        this.logger = LoggerFactory.getLogger(this.getClass());

        // Check if the config file exists, if not, create it with default values
        File configFile = new File("bot.conf");
        if (!configFile.exists()) {
            this.createDefaultConfigFile(configFile.toPath());
        }

        // Load the config
        this.config = ConfigFactory.parseFile(configFile);
    }

    private void createDefaultConfigFile(Path configFile) {
        try {
            // Create parent directories if they don't exist
            Files.createDirectories(configFile.getParent());

            // Write default config to the file
            Files.copy(
                    this.getClass().getResourceAsStream("/bot.conf"),
                    configFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

            this.logger.info("Default config file created: {}", configFile);
        } catch (IOException ex) {
            this.logger.error("Failed to create default config file!", ex);
        }
    }
}
