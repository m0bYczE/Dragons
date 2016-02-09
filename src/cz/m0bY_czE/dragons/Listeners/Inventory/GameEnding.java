package cz.m0bY_czE.dragons.Listeners.Inventory;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.Scoreboard;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Threads.EndingTasks;
import cz.m0bY_czE.dragons.Utilities.InventoryUtilities;
import cz.m0bY_czE.dragons.Utilities.LocationUtilities;

public class GameEnding extends DeadEndListener {
    boolean canWin = true;

    public GameEnding() {
    }

    public static void gameEnding() {
        Iterator var1 = Bukkit.getOnlinePlayers().iterator();

        while(var1.hasNext()) {
            final Player player = (Player)var1.next();
            Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
                public void run() {
                    Scoreboard.instance.arenaChange(player);
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(PlayerDeath.mapCenter);
                }
            }, 2L);
            player.getWorld().playSound(player.getLocation(), Sound.COW_IDLE, 1.0F, 1.0F);
            EndingTasks.instance.runEndingFireworks();
            GameState.setState(GameState.RESETTING);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    player.setGameMode(GameMode.SURVIVAL);
                    InventoryUtilities.instance.clearInventory(player);
                    LocationUtilities.instance.teleportAllToSpawn();
                    Scoreboard.instance.onLobbyJoin(player);
                    Scoreboard.instance.lobbyChange(player);
                }
            }, 200L);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    EndingTasks.instance.noteTask();
                    EndingTasks.instance.kickTask();
                    EndingTasks.instance.reloadTask();
                }
            }, 200L);
        }

    }
}
