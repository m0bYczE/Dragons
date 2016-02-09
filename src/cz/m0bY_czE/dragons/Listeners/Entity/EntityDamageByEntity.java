package cz.m0bY_czE.dragons.Listeners.Entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class EntityDamageByEntity extends DeadEndListener {
    public EntityDamageByEntity() {
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByFall(EntityDamageEvent event) {
        if(GameState.getState() != GameState.IN_GAME && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}

