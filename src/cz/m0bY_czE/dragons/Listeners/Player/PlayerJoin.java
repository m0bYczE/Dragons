package cz.m0bY_czE.dragons.Listeners.Player;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Handlers.Scoreboard;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Utilities.InventoryUtilities;
import cz.m0bY_czE.dragons.Utilities.LocationUtilities;

public class PlayerJoin extends DeadEndListener {
    public static int playersToStart = 4;

    public PlayerJoin() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Game.instance.setCanStart(Bukkit.getOnlinePlayers().size() >= playersToStart);
        LocationUtilities.instance.teleportToSpawn(player);
        InventoryUtilities.instance.clearInventory(player);
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        Scoreboard.instance.onLobbyJoin(player);
        player.setGameMode(GameMode.SURVIVAL);
        GameState.setState(GameState.IN_LOBBY);
        player.getInventory().setHeldItemSlot(0);
        Iterator lobbyselectorMeta = Bukkit.getOnlinePlayers().iterator();

        while(lobbyselectorMeta.hasNext()) {
            Player lobbyselector = (Player)lobbyselectorMeta.next();
            Scoreboard.instance.lobbyChange(lobbyselector);
            lobbyselector.removePotionEffect(PotionEffectType.JUMP);
        }

        ItemStack lobbyselector1 = new ItemStack(Material.NETHER_STAR);
        ItemMeta lobbyselectorMeta1 = lobbyselector1.getItemMeta();
        lobbyselectorMeta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bLobby Selector"));
        lobbyselector1.setItemMeta(lobbyselectorMeta1);
        player.getInventory().setItem(8, new ItemStack(lobbyselector1));
    }
}

