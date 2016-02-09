package cz.m0bY_czE.dragons.Utilities;

import java.util.Iterator;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.Main;

public class LocationUtilities {
    public static LocationUtilities instance;
    World w;
    private Location spawnLocation;
    Random dice;
    int location;

    public LocationUtilities() {
        this.w = Bukkit.getServer().getWorld(Main.instance.getConfig().getString("arena-world"));
        this.spawnLocation = this.decodeLocation("spawn-location");
        this.dice = new Random();
        this.location = 0;
        instance = this;
    }

    public Location decodeLocation(String s) {
        String loc = Main.instance.getConfig().getString("Locations." + s);
        String[] arg = loc.split(",");
        double[] parsed = new double[3];

        for(int location = 0; location < 3; ++location) {
            parsed[location] = Double.parseDouble(arg[location + 1]);
        }

        Location var6 = new Location(Bukkit.getWorld(arg[0]), parsed[0], parsed[1], parsed[2]);
        return var6;
    }

    public Location getSpawn1(Player player) {
        World w = Bukkit.getServer().getWorld("Arena");
        float yaw = 0.5F;
        float pitch = -3.6F;
        double x = 4.503D;
        double y = 45.0D;
        double z = -8.535D;
        Location loc = new Location(w, x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        return loc;
    }

    public Location getSpawn2(Player player) {
        World w = Bukkit.getServer().getWorld("Arena");
        float yaw = 0.3F;
        float pitch = -4.1F;
        double x = -4.566D;
        double y = 45.0D;
        double z = -8.489D;
        Location loc = new Location(w, x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        return loc;
    }

    public Location getSpawn3(Player player) {
        World w = Bukkit.getServer().getWorld("Arena");
        float yaw = 178.8F;
        float pitch = -3.3F;
        double x = -1.46D;
        double y = 45.0D;
        double z = 4.45D;
        Location loc = new Location(w, x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        return loc;
    }

    public Location getSpawn4(Player player) {
        World w = Bukkit.getServer().getWorld("Arena");
        float yaw = 179.3F;
        float pitch = -3.6F;
        double x = 6.486D;
        double y = 45.0D;
        double z = 4.515D;
        Location loc = new Location(w, x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        return loc;
    }

    public Location teleportToSpawn(Player p) {
        Location spawn = this.spawnLocation;
        float yaw = (float)Main.instance.getConfig().getInt("spawn-yaw");
        float pitch = (float)Main.instance.getConfig().getInt("spawn-pitch");
        spawn.setYaw(yaw);
        spawn.setPitch(pitch);
        p.teleport(this.spawnLocation);
        return spawn;
    }

    public void teleportAllToSpawn() {
        Iterator var2 = Bukkit.getOnlinePlayers().iterator();

        while(var2.hasNext()) {
            Player p = (Player)var2.next();
            this.teleportToSpawn(p);
        }

    }

    public Location nextSpawnLocation(Player player) {
        ++this.location;
        switch(this.location) {
            case 1:
                player.teleport(this.getSpawn1(player));
                break;
            case 2:
                player.teleport(this.getSpawn2(player));
                break;
            case 3:
                player.teleport(this.getSpawn3(player));
                break;
            case 4:
                player.teleport(this.getSpawn4(player));
        }

        return this.getSpawn1(player);
    }
}

