package me.vik1395.bungeekick;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public record BungeeKickConfiguration(String serverName, String kickMessage, boolean showKickMessage) {

  public TextComponent kickMessageTextComponent(BaseComponent[] kickReasonComponent) {
    final var kickMessage = ChatColor.translateAlternateColorCodes('&', kickMessage());
    final var kickReasonMessage = ChatColor.stripColor(BaseComponent.toLegacyText(kickReasonComponent));

    return new TextComponent(kickMessage + kickReasonMessage);
  }

}
