package cz.m0bY_czE.dragons.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerJoin;

public class Scoreboard extends DeadEndListener {
    public static Scoreboard instance;

    public Scoreboard() {
        instance = this;
    }

    public void onLobbyJoin(Player player) {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + player.getName() + " group set default");
    }

    @EventHandler
    public void onJoinScoreboard(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.lobbyChange(player);
    }

    public void lobbyChange(Player player) {
        SimpleScoreboard scoreboard = new SimpleScoreboard("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Dragons");
        scoreboard.add(ChatColor.AQUA + " ");
        scoreboard.add("" + ChatColor.YELLOW + ChatColor.BOLD + "Players:");
        scoreboard.add(Bukkit.getOnlinePlayers().size() + "/" + PlayerJoin.playersToStart);
        scoreboard.add(ChatColor.AQUA + "  ");
        scoreboard.add("" + ChatColor.YELLOW + ChatColor.BOLD + "Players to start:");
        scoreboard.add("2");
        scoreboard.add(ChatColor.AQUA + "   ");
        scoreboard.add("" + ChatColor.YELLOW + ChatColor.BOLD + "Max. Players:");
        scoreboard.add("4");
        scoreboard.build();
        scoreboard.send(new Player[]{player});
    }

    public void arenaChange(Player player) {
        SimpleScoreboard scoreboard = new SimpleScoreboard("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Dragons");
        scoreboard.add(ChatColor.AQUA + " ");
        scoreboard.add("" + ChatColor.GREEN + ChatColor.BOLD + "Players Alive:");
        scoreboard.add("" + ChatColor.RESET + PlayerDeath.aliveplayers);
        scoreboard.add(ChatColor.AQUA + "  ");
        scoreboard.add("" + ChatColor.RED + ChatColor.BOLD + "Players Dead:");
        scoreboard.add("" + ChatColor.RESET + PlayerDeath.deadplayers);
        scoreboard.build();
        scoreboard.send(new Player[]{player});
    }
}

