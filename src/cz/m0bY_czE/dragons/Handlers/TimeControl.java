package cz.m0bY_czE.dragons.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import cz.m0bY_czE.dragons.Main;

public class TimeControl extends BukkitRunnable {
    Main plugin;
    World spawn;
    World arena;

    public TimeControl(Main pl) {
        this.spawn = Bukkit.getServer().getWorld(Main.instance.getConfig().getString("lobby-world"));
        this.arena = Bukkit.getServer().getWorld(Main.instance.getConfig().getString("arena-world"));
        this.plugin = pl;
    }

    public void run() {
        this.spawn.setTime(8000L);
        this.arena.setTime(8000L);
    }
}

