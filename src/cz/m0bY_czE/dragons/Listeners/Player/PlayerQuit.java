package cz.m0bY_czE.dragons.Listeners.Player;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Handlers.Prefix;
import cz.m0bY_czE.dragons.Handlers.Scoreboard;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Listeners.Inventory.GameEnding;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerJoin;
import cz.m0bY_czE.dragons.Threads.EndingTasks;
import cz.m0bY_czE.dragons.Utilities.ChatUtilities;

public class PlayerQuit extends DeadEndListener {
    public PlayerQuit() {
    }

    @EventHandler
    public void onCustomQuit(PlayerQuitEvent ev) {
        Player p = ev.getPlayer();
        ev.setQuitMessage(Prefix.instance.prefix + ChatColor.DARK_GRAY + p.getName() + " has left.");
        final Player player;
        Iterator var4;
        if(GameState.getState() == GameState.IN_LOBBY || GameState.getState() == GameState.PRE_GAME || GameState.getState() == GameState.RESETTING) {
            var4 = Bukkit.getOnlinePlayers().iterator();

            while(var4.hasNext()) {
                player = (Player)var4.next();
                Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
                    public void run() {
                        Scoreboard.instance.lobbyChange(player);
                    }
                }, 2L);
            }
        }

        if(GameState.getState() == GameState.IN_GAME && !PlayerDeath.deathlist.contains(p)) {
            --PlayerDeath.aliveplayers;
            ++PlayerDeath.deadplayers;
            PlayerDeath.deathlist.add(p);
            Game.instance.removePlayer(p);
        }

        if(GameState.getState() == GameState.IN_GAME && PlayerDeath.aliveplayers == 1) {
            var4 = Bukkit.getOnlinePlayers().iterator();

            while(var4.hasNext()) {
                player = (Player)var4.next();
                ChatUtilities.instance.sendTitle(player, Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.RED + PlayerDeath.winner() + ChatColor.YELLOW + " have won! ", ChatColor.GOLD + "Let me take you to the lobby.");
            }

            GameEnding.gameEnding();
        }

        if(Game.instance.hasStarted()) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + p.getName() + " delete");
            var4 = Bukkit.getOnlinePlayers().iterator();

            while(var4.hasNext()) {
                player = (Player)var4.next();
                Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
                    public void run() {
                        Scoreboard.instance.arenaChange(player);
                    }
                }, 2L);
            }
        }

        if(GameState.isState(GameState.IN_LOBBY)) {
            Game.instance.setCanStart(Bukkit.getOnlinePlayers().size() - 1 >= PlayerJoin.playersToStart);
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onServerEmpty(PlayerQuitEvent e) {
        if(GameState.getState() != GameState.IN_LOBBY && Bukkit.getOnlinePlayers().size() - 1 <= 0) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    EndingTasks.instance.reloadTask();
                }
            }, 50L);
        }

    }
}

