package cz.m0bY_czE.dragons.Handlers;

import org.bukkit.ChatColor;

import cz.m0bY_czE.dragons.Main;

public class Prefix {
    public static Prefix instance;
    public String prefix;

    public Prefix() {
        this.prefix = ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("prefix"));
        instance = this;
    }

    public String getPrefix() {
        return this.prefix;
    }
}

