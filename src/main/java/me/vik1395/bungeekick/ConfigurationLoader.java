package me.vik1395.bungeekick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public record ConfigurationLoader(File dataFolder) {
  private static final String configFileName = "config.yml";

  private void writeDefaultConfigIfConfigNotExists() throws IOException {
    if (!dataFolder.exists()) {
      dataFolder.mkdirs();
    }

    final var configFile = new File(dataFolder, configFileName);
    if (!configFile.exists()) {
      try (final var stream = this.getClass().getClassLoader().getResourceAsStream(configFileName)) {
        Files.copy(stream, configFile.toPath());
      }
    }
  }

  public BungeeKickConfiguration loadUpToDateConfig() throws IOException {
    writeDefaultConfigIfConfigNotExists();

    final var configFile = new File(dataFolder, configFileName);

    final var cProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    final var loadedConfig = cProvider.load(configFile);

    return new BungeeKickConfiguration(
        loadedConfig.getString("ServerName"),
        loadedConfig.getString("KickMessage"),
        loadedConfig.getBoolean("ShowKickMessage"));
  }
}
