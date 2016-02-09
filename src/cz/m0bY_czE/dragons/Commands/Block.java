package cz.m0bY_czE.dragons.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import cz.m0bY_czE.dragons.Handlers.Prefix;

public class Block implements Listener {
    public Block() {
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if(e.getMessage().equalsIgnoreCase("/plugins")) {
            if(!p.hasPermission("dragons.permission")) {
                e.setCancelled(true);
                p.sendMessage(Prefix.instance.prefix + ChatColor.RED + "You don\'t have permission to do this.");
            }

        } else if(e.getMessage().equalsIgnoreCase("/kill")) {
            if(!p.hasPermission("dragons.permission")) {
                e.setCancelled(true);
                p.sendMessage(Prefix.instance.prefix + ChatColor.RED + "You don\'t have permission to do this.");
            }

        } else if(e.getMessage().equalsIgnoreCase("/pl")) {
            if(!p.hasPermission("dragons.permission")) {
                e.setCancelled(true);
                p.sendMessage(Prefix.instance.prefix + ChatColor.RED + "You don\'t have permission to do this.");
            }

        } else if(e.getMessage().equalsIgnoreCase("/?")) {
            if(!p.hasPermission("dragons.permission")) {
                e.setCancelled(true);
                p.sendMessage(Prefix.instance.prefix + ChatColor.RED + "You don\'t have permission to do this.");
            }

        }
    }
}

