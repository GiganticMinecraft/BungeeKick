package me.vik1395.bungeekick;

import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
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

public class PlayerListener implements Listener {
    BungeeKick plugin;

    public PlayerListener(BungeeKick plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerKickEvent(ServerKickEvent ev) {
        final ProxiedPlayer player = ev.getPlayer();
        final ProxyServer proxy = this.plugin.getProxy();

        ServerInfo kickedFrom = null;

        if (player.getServer() != null) {
            kickedFrom = player.getServer().getInfo();
        } else if (proxy.getReconnectHandler() != null) {
            kickedFrom = proxy.getReconnectHandler().getServer(player);
        } else {
            final PendingConnection pendingConnection = player.getPendingConnection();
            kickedFrom = AbstractReconnectHandler.getForcedHost(pendingConnection);

            if (kickedFrom == null) {
                kickedFrom = ProxyServer.getInstance()
                        .getServerInfo(pendingConnection.getListener().getServerPriority().get(0));
            }
        }

        final ServerInfo kickTo = proxy.getServerInfo(BungeeKick.config.getString("ServerName"));

        if (kickedFrom != null && kickedFrom.equals(kickTo)) {
            return;
        }

        ev.setCancelled(true);
        ev.setCancelServer(kickTo);

        if (BungeeKick.config.getBoolean("ShowKickMessage")) {
            String msg = BungeeKick.config.getString("KickMessage");
            msg = ChatColor.translateAlternateColorCodes('&', msg);
            String kmsg = ChatColor.stripColor(BaseComponent.toLegacyText(ev.getKickReasonComponent()));
            msg = msg + kmsg;
            ev.getPlayer().sendMessage(new TextComponent(msg));
        }
    }
}
