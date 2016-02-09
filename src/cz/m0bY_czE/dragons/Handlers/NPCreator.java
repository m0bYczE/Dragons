package cz.m0bY_czE.dragons.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EnderDragon;

public class NPCreator {
    public NPCreator() {
    }

    public static void DragonNPC() {
        Location loc = new Location(Bukkit.getServer().getWorld("Arena"), -2.504D, 54.0D, 22.554D);

        for(int i = 0; i < 4; ++i) {
            EnderDragon v = (EnderDragon)loc.getWorld().spawnCreature(loc, CreatureType.ENDER_DRAGON);
            v.setCustomName("" + ChatColor.GOLD + ChatColor.BOLD + "Dragon");
            v.getLocation().setX(-1.46D);
            v.getLocation().setY(45.0D);
            v.getLocation().setZ(4.45D);
        }

    }
}

