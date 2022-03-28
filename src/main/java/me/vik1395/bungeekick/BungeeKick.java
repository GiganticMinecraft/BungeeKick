package me.vik1395.bungeekick;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/*
 * 
 * Author: Vik1395
 * Project: BungeeKick
 * 
 * Copyright 2014
 * 
 * Licensed under Creative CommonsAttribution-ShareAlike 4.0 International Public License (the "License");
 * You may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at http://creativecommons.org/licenses/by-sa/4.0/legalcode
 * 
 * You may find an abridged version of the License at http://creativecommons.org/licenses/by-sa/4.0/
 */

public class BungeeKick extends Plugin {
    public void onEnable() {
        final var cFolder = new File(this.getDataFolder(), "");

        if (!cFolder.exists()) {
            cFolder.mkdir();
        }

        final var cFile = new File(this.getDataFolder() + "/config.yml");
        if (!cFile.exists()) {
            try {
                String file = "ServerName: \'lobby\'\n"
                        + "# This is where the player is kicked to. This is usually the lobby/hub server\n"
                        + "KickMessage: \'&6You have been kicked! Reason:&4 \'\n"
                        + "# Message to be sent to the player who has been kicked. This message is followed by the kick reason"
                        + "ShowKickMessage: True\n"
                        + "# Set this to True if you want the kicked player to be able to see the kick reason.";

                FileWriter fw = new FileWriter(cFile);
                BufferedWriter out = new BufferedWriter(fw);
                out.write(file);
                out.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            final var cProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            final var loadedConfig = cProvider.load(cFile);

            final var config = new BungeeKickConfiguration(
                    loadedConfig.getString("ServerName"),
                    loadedConfig.getString("KickMessage"),
                    loadedConfig.getBoolean("ShowKickMessage"));

            final var listener = new PlayerListener(this.getProxy(), config);

            this.getProxy().getPluginManager().registerListener(this, listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
