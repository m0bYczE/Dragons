package cz.m0bY_czE.dragons.Threads;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Handlers.LobbyKick;
import cz.m0bY_czE.dragons.Handlers.NPCreator;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Utilities.ChatUtilities;
import cz.m0bY_czE.dragons.Utilities.CustomEntityFirework;

public class EndingTasks {
    public static EndingTasks instance;
    public int FireworkTask;
    public int ReloadTask;
    public int NoteTask;
    public int PauseTask;
    public int KickTask;
    public int InfoTask;
    public int DelayTask1;
    public int DelayTask2;
    public int DelayTask3;
    public int DelayTask4;

    public EndingTasks() {
        instance = this;
    }

    public void runEndingFireworks() {
        this.FireworkTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            public void run() {
                Random r = new Random();
                int fType = r.nextInt(5) + 1;
                Type type = null;
                switch(fType) {
                    case 1:
                    default:
                        type = Type.BALL;
                        break;
                    case 2:
                        type = Type.BALL_LARGE;
                        break;
                    case 3:
                        type = Type.BURST;
                        break;
                    case 4:
                        type = Type.CREEPER;
                        break;
                    case 5:
                        type = Type.STAR;
                }

                int c1i = r.nextInt(16) + 1;
                int c2i = r.nextInt(16) + 1;
                Color c1 = EndingTasks.getColor(c1i);
                Color c2 = EndingTasks.getColor(c2i);
                CustomEntityFirework.spawn(PlayerDeath.mapCenter.add(0.0D, 2.0D, 0.0D), FireworkEffect.builder().with(Type.BURST).withColor(c1).withFade(c2).flicker(false).trail(r.nextBoolean()).build());
            }
        }, 1L, 20L);
    }

    public static Color getColor(int c) {
        switch(c) {
            case 1:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.PURPLE;
            case 12:
                return Color.RED;
            case 13:
                return Color.SILVER;
            case 14:
                return Color.TEAL;
            default:
                return Color.WHITE;
        }
    }

    public void reloadTask() {
        this.ReloadTask = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Bukkit.getServer().shutdown();
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player player = (Player)var2.next();
                    player.kickPlayer("" + ChatColor.RED + ChatColor.BOLD + "Server is restarting!");
                }

            }
        }, 120L);
    }

    public void infoTask(final Player player) {
        this.InfoTask = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                player.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "=========================================");
                player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Dead End");
                player.sendMessage("" + ChatColor.GREEN);
                player.sendMessage(ChatColor.GOLD + " Running will destroy blocks under you, allowing");
                player.sendMessage(ChatColor.GOLD + " other players to fall into the pit. The object");
                player.sendMessage(ChatColor.GOLD + " of the game is the be the last player on the");
                player.sendMessage(ChatColor.GOLD + " field!");
                player.sendMessage("" + ChatColor.GREEN);
                player.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "http://www.mineverse.com/");
                player.sendMessage("" + ChatColor.AQUA + ChatColor.BOLD + "=========================================");
                player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }, 10L);
    }

    public void delayTask1() {
        this.DelayTask1 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    ChatUtilities.instance.sendTitle(p.getPlayer(), Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "3"), ChatColor.translateAlternateColorCodes('&', "" + ChatColor.YELLOW));
                    p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
                }

            }
        }, 100L);
    }

    public void delayTask2() {
        this.DelayTask2 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    ChatUtilities.instance.sendTitle(p.getPlayer(), Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "2"), ChatColor.translateAlternateColorCodes('&', "" + ChatColor.YELLOW));
                    p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
                }

            }
        }, 120L);
    }

    public void delayTask3() {
        this.DelayTask3 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    ChatUtilities.instance.sendTitle(p.getPlayer(), Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + "1"), ChatColor.translateAlternateColorCodes('&', "" + ChatColor.YELLOW));
                    p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
                }

            }
        }, 140L);
    }

    public void delayTask4() {
        this.DelayTask4 = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    ChatUtilities.instance.sendTitle(p.getPlayer(), Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN + "Go!"), ChatColor.translateAlternateColorCodes('&', "" + ChatColor.RED));
                    GameState.setState(GameState.IN_GAME);
                    p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
                    EndingTasks.this.sendAction(p, "" + ChatColor.YELLOW + ChatColor.BOLD + "TIP " + ChatColor.RED + "Hold sprint at all times and do not run backwards.");
                    NPCreator.DragonNPC();
                }

            }
        }, 160L);
    }

    public void pauseTask() {
        this.PauseTask = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    ChatUtilities.instance.sendTitle(p.getPlayer(), Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.AQUA + "Dead End", ChatColor.RED + "The floor is falling out under you!");
                }

            }
        }, 10L);
    }

    public void noteTask() {
        this.NoteTask = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    ChatUtilities.instance.sendTitle(p.getPlayer(), Integer.valueOf(10), Integer.valueOf(50), Integer.valueOf(100), ChatColor.GREEN + "Thanks for playing!", ChatColor.GOLD + "Server will restart in 5 seconds...");
                }

            }
        }, 30L);
    }

    public void kickTask() {
        this.KickTask = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player player = (Player)var2.next();
                    LobbyKick.doKick(player);
                }

                Bukkit.unloadWorld(Main.instance.getConfig().getString("arena-world"), false);
            }
        }, 115L);
    }

    public void sendAction(Player player, String message) {
        IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bar);
    }
}

