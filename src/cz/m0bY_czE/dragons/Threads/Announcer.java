package cz.m0bY_czE.dragons.Threads;

import java.util.Iterator;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;

public class Announcer {
    public static Announcer instance;
    Random dice = new Random();
    public int ruleTask;
    public String Rule1;
    public String Rule2;
    public String Rule3;

    public Announcer() {
        this.Rule1 = ChatColor.AQUA + "Run across a layer of snow without falling through the holes that appear.";
        this.Rule2 = ChatColor.YELLOW + "When players hit bedrock, their game is over and they are taken out of the arena.";
        this.Rule3 = ChatColor.LIGHT_PURPLE + "The last player alive is the winner.";
        instance = this;
    }

    public void showRules() {
        if(GameState.getState() == GameState.IN_LOBBY) {
            this.ruleTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
                public void run() {
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + ChatColor.BOLD + "HINT " + Announcer.this.randRule());
                    Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                    while(var2.hasNext()) {
                        Player player = (Player)var2.next();
                        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F);
                    }

                }
            }, 130L, 350L);
        }

    }

    public String randRule() {
        switch(this.dice.nextInt(3)) {
            case 0:
                return this.Rule1;
            case 1:
                return this.Rule2;
            case 2:
                return this.Rule3;
            default:
                return this.Rule1;
        }
    }
}

