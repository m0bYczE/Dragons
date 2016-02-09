package cz.m0bY_czE.dragons.Threads;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.ServerListPingEvent;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class ServerListPing extends DeadEndListener {
    public ServerListPing() {
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onServerListPing(ServerListPingEvent e) {
        GameState state = GameState.getState();
        if(state == GameState.IN_LOBBY) {
            e.setMotd(ChatColor.GREEN + "Recruiting...");
        }

        if(state == GameState.PRE_GAME) {
            e.setMotd(ChatColor.YELLOW + "Pre-Game...");
        }

        if(state == GameState.IN_GAME) {
            e.setMotd(ChatColor.YELLOW + "Game is in progress...");
        }

        if(state == GameState.RESETTING) {
            e.setMotd(ChatColor.RED + "Server is restarting...");
        }

    }
}

