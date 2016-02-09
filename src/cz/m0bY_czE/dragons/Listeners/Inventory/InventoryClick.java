package cz.m0bY_czE.dragons.Listeners.Inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class InventoryClick extends DeadEndListener {
    Main plugin;

    public InventoryClick() {
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
