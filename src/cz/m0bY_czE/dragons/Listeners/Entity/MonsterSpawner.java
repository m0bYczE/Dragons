package cz.m0bY_czE.dragons.Listeners.Entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class MonsterSpawner extends DeadEndListener {
    public MonsterSpawner() {
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != SpawnReason.CUSTOM && event.getSpawnReason() != SpawnReason.SPAWNER_EGG) {
            event.setCancelled(true);
        }
    }
}

