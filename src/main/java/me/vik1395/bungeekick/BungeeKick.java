package me.vik1395.bungeekick;

import java.io.IOException;

import net.md_5.bungee.api.plugin.Plugin;

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
        try {
            final var config = new ConfigurationLoader(this.getDataFolder()).loadUpToDateConfig();
            final var listener = new PlayerKickListener(this.getProxy(), config);

            this.getProxy().getPluginManager().registerListener(this, listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
