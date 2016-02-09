package cz.m0bY_czE.dragons.Handlers;

import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

class Message implements OfflinePlayer {
    String name;

    public Message(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Object> serialize() {
        return null;
    }

    public UUID getUniqueId() {
        return UUID.randomUUID();
    }

    public boolean isOp() {
        return false;
    }

    public void setOp(boolean value) {
    }

    public Location getBedSpawnLocation() {
        return null;
    }

    public long getFirstPlayed() {
        return 0L;
    }

    public long getLastPlayed() {
        return 0L;
    }

    public Player getPlayer() {
        return null;
    }

    public boolean hasPlayedBefore() {
        return false;
    }

    public boolean isBanned() {
        return false;
    }

    public boolean isOnline() {
        return false;
    }

    public boolean isWhitelisted() {
        return false;
    }

    public void setBanned(boolean banned) {
    }

    public void setWhitelisted(boolean value) {
    }
}

