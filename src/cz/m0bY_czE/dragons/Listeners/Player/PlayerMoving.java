package cz.m0bY_czE.dragons.Listeners.Player;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Handlers.Scoreboard;
import cz.m0bY_czE.dragons.Listeners.Inventory.GameEnding;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Utilities.ChatUtilities;
import cz.m0bY_czE.dragons.Utilities.CustomEntityFirework;

public class PlayerMoving implements Listener {
    public PlayerMoving() {
    }

    public static String winner() {
        Iterator var1 = Game.instance.getAlivePlayers().iterator();
        if(var1.hasNext()) {
            String s = (String)var1.next();
            StringBuilder sb = new StringBuilder();
            sb.append(Game.instance.getAlivePlayers() + ", ");
            s = sb.toString();
            Pattern pattern = Pattern.compile(", $");
            Matcher matcher = pattern.matcher(s);
            s = matcher.replaceAll("");
            return s;
        } else {
            return winner();
        }
    }

    @EventHandler
    public void onPlayerMoving(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerBlock = player.getLocation().add(0.0D, -1.0D, 0.0D);
        if(GameState.getState() == GameState.IN_GAME) {
            final Player p;
            Iterator var5;
            if(playerBlock.getBlock().getType().equals(Material.BEDROCK)) {
                PlayerDeath.deathlist.add(player);
                --PlayerDeath.aliveplayers;
                ++PlayerDeath.deadplayers;
                Game.instance.removePlayer(player);
                CustomEntityFirework.spawn(player.getLocation().add(0.0D, -0.15D, 0.0D), FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).flicker(false).trail(false).build());
                var5 = Bukkit.getOnlinePlayers().iterator();

                while(var5.hasNext()) {
                    player = (Player)var5.next();
                    Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
                        public void run() {
                            Scoreboard.instance.arenaChange(p);
                        }
                    }, 2L);
                }
            }

            if(PlayerDeath.aliveplayers == 2) {
                var5 = Bukkit.getOnlinePlayers().iterator();

                while(var5.hasNext()) {
                    p = (Player)var5.next();
                    ChatUtilities.instance.sendTitle(p, Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.RED + winner() + ChatColor.YELLOW + " have won! ", ChatColor.GOLD + "Let me take you to the lobby.");
                }

                GameEnding.gameEnding();
            }
        }

    }
}

