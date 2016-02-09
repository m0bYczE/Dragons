package cz.m0bY_czE.dragons.Listeners.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Handlers.Scoreboard;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Listeners.Inventory.GameEnding;
import cz.m0bY_czE.dragons.Utilities.ChatUtilities;
import cz.m0bY_czE.dragons.Utilities.CustomEntityFirework;
import cz.m0bY_czE.dragons.Utilities.LocationUtilities;

public class PlayerDeath extends DeadEndListener {
    public static Location mapCenter = decodeLocation("map-center");
    public static ArrayList<Player> deathlist = new ArrayList();
    public static int aliveplayers = 0;
    public static int deadplayers = 0;

    public PlayerDeath() {
    }

    public static Location decodeLocation(String s) {
        String loc = Main.instance.getConfig().getString("Locations." + s);
        String[] arg = loc.split(",");
        double[] parsed = new double[3];

        for(int location = 0; location < 3; ++location) {
            parsed[location] = Double.parseDouble(arg[location + 1]);
        }

        Location var5 = new Location(Bukkit.getWorld(arg[0]), parsed[0], parsed[1], parsed[2]);
        return var5;
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
    public void entityShoot(EntityDeathEvent e) {
        e.getDrops().clear();
        e.setDroppedExp(0);
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = e.getEntity().getKiller();
        if(e.getEntity().getKiller() instanceof Player) {
            killer.getInventory().addItem(new ItemStack[]{new ItemStack(Material.ARROW)});
        }

        e.getDrops().clear();
        deathlist.add(p);
        --aliveplayers;
        ++deadplayers;
        Game.instance.removePlayer(p);
        CustomEntityFirework.spawn(p.getLocation().add(0.0D, -0.15D, 0.0D), FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).flicker(false).trail(false).build());
        Iterator var5 = Bukkit.getOnlinePlayers().iterator();

        final Player player;
        while(var5.hasNext()) {
            player = (Player)var5.next();
            Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
                public void run() {
                    Scoreboard.instance.arenaChange(player);
                }
            }, 2L);
        }

        if(aliveplayers == 1) {
            var5 = Bukkit.getOnlinePlayers().iterator();

            while(var5.hasNext()) {
                player = (Player)var5.next();
                ChatUtilities.instance.sendTitle(player, Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.RED + winner() + ChatColor.YELLOW + " have won! ", ChatColor.GOLD + "Let me take you to the lobby.");
            }

            GameEnding.gameEnding();
        }

    }

    @EventHandler
    public void onPlayerDeathMessage(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        DamageCause cause = player.getLastDamageCause().getCause();
        String victim = ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY;
        String damager = killer == null?"monster":ChatColor.YELLOW + killer.getName() + ChatColor.DARK_GRAY;
        if(cause != DamageCause.BLOCK_EXPLOSION && cause != DamageCause.ENTITY_EXPLOSION) {
            if(cause == DamageCause.CONTACT) {
                event.setDeathMessage(victim + " ran into a cactus.");
            } else if(cause != DamageCause.CUSTOM && cause != DamageCause.MAGIC) {
                if(cause == DamageCause.DROWNING) {
                    event.setDeathMessage(victim + " drowned.");
                } else if(cause == DamageCause.ENTITY_ATTACK) {
                    event.setDeathMessage(victim + " was cruelly killed by " + damager + ".");
                } else if(cause == DamageCause.FALL) {
                    event.setDeathMessage(victim + " tried to test if the gravity was on.");
                } else if(cause == DamageCause.FALLING_BLOCK) {
                    event.setDeathMessage(victim + " was crushed.");
                } else if(cause != DamageCause.FIRE && cause != DamageCause.FIRE_TICK) {
                    if(cause == DamageCause.LAVA) {
                        event.setDeathMessage(victim + " was burned to bones.");
                    } else if(cause == DamageCause.LIGHTNING) {
                        event.setDeathMessage(victim + " was wrecked by Zeus himself.");
                    } else if(cause == DamageCause.POISON) {
                        event.setDeathMessage(victim + " was poisoned.");
                    } else if(cause == DamageCause.PROJECTILE) {
                        event.setDeathMessage(victim + " was precisely sniped by " + damager + ".");
                    } else if(cause == DamageCause.STARVATION) {
                        event.setDeathMessage(victim + " starved to death.");
                    } else if(cause == DamageCause.SUFFOCATION) {
                        event.setDeathMessage(victim + " suffocated.");
                    } else if(cause == DamageCause.SUICIDE) {
                        event.setDeathMessage(victim + " gave up on life.");
                    } else if(cause == DamageCause.THORNS) {
                        event.setDeathMessage(victim + " was pickled by " + damager + "\'s thorns.");
                    } else if(cause == DamageCause.VOID) {
                        event.setDeathMessage(victim + " fell out of the world.");
                    } else if(cause == DamageCause.WITHER) {
                        event.setDeathMessage(victim + " was killed by a Wither.");
                    } else {
                        event.setDeathMessage(victim + " died.");
                    }
                } else {
                    event.setDeathMessage(victim + " was roasted.");
                }
            } else {
                event.setDeathMessage(victim + " was killed by magic.");
            }
        } else {
            event.setDeathMessage(victim + " exploded.");
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        event.setRespawnLocation(mapCenter);
        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onVoidEnter(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(e.getPlayer().getLocation().getY() < 0.0D && (GameState.getState() != GameState.IN_GAME || GameState.getState() != GameState.PRE_GAME)) {
            e.getPlayer().teleport(LocationUtilities.instance.teleportToSpawn(player));
        }

    }
}

