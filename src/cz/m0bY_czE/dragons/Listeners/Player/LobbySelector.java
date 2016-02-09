package cz.m0bY_czE.dragons.Listeners.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cz.m0bY_czE.dragons.Main;

public class LobbySelector implements Listener {
    public LobbySelector() {
    }

    @EventHandler
    public void onCompassClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player p = event.getPlayer();

            try {
                if(p.getItemInHand().getType() == Material.NETHER_STAR && p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bLobby Selector"))) {
                    event.setCancelled(true);
                    p.playSound(p.getLocation(), Sound.CLICK, 7.0F, 1.0F);
                    openLobbySelector(p);
                }
            } catch (Exception var4) {
                ;
            }
        }

    }

    public static void openLobbySelector(Player p) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.translateAlternateColorCodes('&', "Lobby Selector"));
        ItemStack istack = new ItemStack(Material.NETHER_STAR);
        ItemMeta imeta = istack.getItemMeta();
        imeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lLobby 1"));
        istack.setItemMeta(imeta);
        inv.setItem(11, istack);
        ItemStack istack2 = new ItemStack(Material.NETHER_STAR);
        ItemMeta imeta2 = istack.getItemMeta();
        imeta2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lLobby 2"));
        istack2.setItemMeta(imeta2);
        inv.setItem(13, istack2);
        ItemStack istack3 = new ItemStack(Material.NETHER_STAR);
        ItemMeta imeta3 = istack.getItemMeta();
        imeta3.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lLobby 3"));
        istack3.setItemMeta(imeta3);
        inv.setItem(15, istack3);
        p.openInventory(inv);
    }

    @EventHandler
    public void onClickOnItem(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            Player p = (Player)event.getWhoClicked();
            if(event.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', "Lobby Selector")) && event.getSlot() == event.getRawSlot()) {
                event.setCancelled(true);

                try {
                    if(event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().hasItemMeta()) {
                        ItemStack invstack = event.getCurrentItem();
                        if(invstack.getType() == Material.NETHER_STAR && invstack.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lLobby 1"))) {
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 7.0F, 1.0F);
                            Main.switchServer(p, "lobby");
                        }

                        if(invstack.getType() == Material.NETHER_STAR && invstack.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lLobby 2"))) {
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 7.0F, 1.0F);
                            Main.switchServer(p, "lobby2");
                        }

                        if(invstack.getType() == Material.NETHER_STAR && invstack.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lLobby 3"))) {
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 7.0F, 1.0F);
                            Main.switchServer(p, "lobby3");
                        }
                    }
                } catch (Exception var4) {
                    ;
                }
            }
        }

    }
}

