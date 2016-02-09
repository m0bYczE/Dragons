package cz.m0bY_czE.dragons.Threads;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerJoin;
import cz.m0bY_czE.dragons.Utilities.ChatUtilities;

public class StartCountdown extends BukkitRunnable {
    public static StartCountdown instance;
    Main plugin;
    public int timeUntilStart;

    public StartCountdown(Main pl) {
        this.plugin = pl;
        instance = this;
    }

    public void run() {
        Player player;
        Iterator var2;
        if(this.timeUntilStart == 0) {
            if(!Game.instance.canStart()) {
                this.plugin.restartCountdown();
                ChatUtilities.instance.Broadcast(ChatColor.BLUE + "There must be " + ChatColor.GOLD + PlayerJoin.playersToStart + ChatColor.BLUE + " players for a game to start.");
                var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    player = (Player)var2.next();
                    player.getWorld().playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }

                return;
            }

            Game.instance.start();
        }

        if(this.timeUntilStart % 30 == 0 || this.timeUntilStart == 10 || this.timeUntilStart < 6) {
            ChatUtilities.instance.Broadcast(ChatColor.YELLOW + String.valueOf(this.timeUntilStart) + ChatColor.GRAY + " seconds until the game starts.");
            var2 = Bukkit.getOnlinePlayers().iterator();

            while(var2.hasNext()) {
                player = (Player)var2.next();
                player.getWorld().playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            }
        }

        --this.timeUntilStart;
    }
}

