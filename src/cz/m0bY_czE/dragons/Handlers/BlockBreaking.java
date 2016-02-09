package cz.m0bY_czE.dragons.Handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreaking implements Listener {
    public BlockBreaking() {
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if(!p.hasPermission("dragons.blockbreak")) {
            event.setCancelled(true);
        }

    }
}

