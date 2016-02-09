package cz.m0bY_czE.dragons.Listeners.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerJoin;

public class AsyncPlayerPreLogin extends DeadEndListener {
    public AsyncPlayerPreLogin() {
    }

    @EventHandler
    public void playerPreLogin(AsyncPlayerPreLoginEvent event) {
        if(Game.instance.hasStarted()) {
            event.disallow(Result.KICK_OTHER, ChatColor.RED + "The game has already started.");
        }

    }

    @EventHandler
    public void playerFullLogin(AsyncPlayerPreLoginEvent event) {
        if(Bukkit.getOnlinePlayers().size() >= PlayerJoin.playersToStart) {
            event.disallow(Result.KICK_OTHER, ChatColor.RED + "Server is full! Please try another server...");
        }

    }
}
