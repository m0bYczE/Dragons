package cz.m0bY_czE.dragons.Listeners.Inventory;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class PlayerInteract extends DeadEndListener {
    public static PlayerInteract instance;

    public PlayerInteract() {
        instance = this;
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getClickedBlock() != null) {
            if(p.getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onProtectInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if(block != null) {
            if(event.getAction() == Action.LEFT_CLICK_BLOCK && block.getRelative(BlockFace.UP).getType() == Material.FIRE) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onSignChange(SignChangeEvent event) {
        for(int i = 0; i < event.getLines().length; ++i) {
            event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
        }

    }
}

