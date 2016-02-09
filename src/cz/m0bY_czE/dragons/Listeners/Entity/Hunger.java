package cz.m0bY_czE.dragons.Listeners.Entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class Hunger extends DeadEndListener {
    public Hunger() {
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player)event.getEntity();
            event.setCancelled(true);
            player.setFoodLevel(20);
        }

    }
}
