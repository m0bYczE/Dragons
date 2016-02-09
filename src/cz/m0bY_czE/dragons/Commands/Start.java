package cz.m0bY_czE.dragons.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Handlers.Prefix;

public class Start implements CommandExecutor {
    Main plugin;

    public Start(Main pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if(cmd.getName().equalsIgnoreCase("forcestart") && player.isOp() && GameState.getState() == GameState.IN_LOBBY) {
            Bukkit.broadcastMessage(Prefix.instance.prefix + ChatColor.RED + ChatColor.BOLD + "Force starting the game!");
            Game.instance.start();
            return true;
        } else {
            player.sendMessage(Prefix.instance.prefix + ChatColor.RED + ChatColor.BOLD + "You cannot do this function!");
            return true;
        }
    }
}
