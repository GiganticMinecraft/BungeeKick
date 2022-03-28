# BungeeKick

BungeeKick works kind of like [MoveMeNow](http://www.spigotmc.org/resources/movemenow.17/).
When players are kicked from a proxied server, they are pushed to the lobby instead of quitting the session,
and are given the kick reason as a message.

BungeeKick doesn't have the Blacklist/Whitelist compatibility like MoveMeNow.
You can try my Bukkit plugin [WhitelistPerm](http://www.spigotmc.org/resources/whitelistperm.1309/) for this.

This repository is a fork of [vik1395/BungeeKick-Minecraft](https://github.com/vik1395/BungeeKick-Minecraft), but with improved [CI](./.github) and [releases](https://github.com/vik1395/BungeeKick-Minecraft/releases) to support setting up a Minecraft-server system declaratively.

## `config.yml`

This can be found under `/plugins/BungeeKick` folder of the BungeeCord installation.

Kick message supports color.
Just use minecraft color codes.
you can use the `&` symbol instead of `§` as well.

```YAML
# This is where the player is kicked to. This is usually the lobby/hub server
ServerName: 'lobby'

# Message to be sent to the player who has been kicked. This message is followed by the kick reason
KickMessage: '&6You have been kicked! Reason: &2'

# Set this to True if you want the kicked player to be able to see the kick reason.
ShowKickMessage: True
```

This plugin is licensed under [CC Attribution-NonCommercial-ShareAlike 4.0 International](http://creativecommons.org/licenses/by-nc-sa/4.0/deed.en_US). 
