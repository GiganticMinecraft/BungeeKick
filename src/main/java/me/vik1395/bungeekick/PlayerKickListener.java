package me.vik1395.bungeekick;

import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
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

public record PlayerKickListener(ProxyServer proxy, BungeeKickConfiguration config) implements Listener {
    private ServerInfo originOfKick(ProxiedPlayer player) {
        if (player.getServer() != null) {
            return player.getServer().getInfo();
        } else if (proxy.getReconnectHandler() != null) {
            return proxy.getReconnectHandler().getServer(player);
        } else {
            final var pendingConnection = player.getPendingConnection();
            final var forcedHost = AbstractReconnectHandler.getForcedHost(pendingConnection);

            if (forcedHost != null) {
                return forcedHost;
            } else {
                final var defaultServer = pendingConnection.getListener().getServerPriority().get(0);
                return proxy.getServerInfo(defaultServer);
            }
        }
    }

    @EventHandler
    public void onServerKickEvent(ServerKickEvent ev) {
        final var player = ev.getPlayer();
        final var kickedFrom = originOfKick(player);
        final var kickTo = proxy.getServerInfo(config.serverName());

        if (kickedFrom != null && kickedFrom.equals(kickTo)) {
            return;
        }

        ev.setCancelled(true);
        ev.setCancelServer(kickTo);

        if (config.showKickMessage()) {
            String msg = config.kickMessage();
            msg = ChatColor.translateAlternateColorCodes('&', msg);
            String kmsg = ChatColor.stripColor(BaseComponent.toLegacyText(ev.getKickReasonComponent()));
            msg = msg + kmsg;
            ev.getPlayer().sendMessage(new TextComponent(msg));
        }
    }
}
