package cz.m0bY_czE.dragons.Utilities;

import java.util.Iterator;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import cz.m0bY_czE.dragons.Handlers.Prefix;
import cz.m0bY_czE.dragons.Listeners.DeadEndListener;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;

public class ChatUtilities extends DeadEndListener {
    public static ChatUtilities instance;
    public final boolean Format = false;
    private String DefaultFormat;
    private String DeadFormat;

    public ChatUtilities() {
        this.DefaultFormat = ChatColor.YELLOW + "%1$s: " + ChatColor.GRAY + "%2$s";
        this.DeadFormat = ChatColor.DARK_GRAY + "Dead " + ChatColor.YELLOW + "%1$s: " + ChatColor.GRAY + "%2$s";
        instance = this;
    }

    public void Broadcast(String msg) {
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player player = (Player)var3.next();
            player.sendMessage(this.starter() + msg);
        }

    }

    public void sendMessage(Player player, String msg) {
        player.sendMessage(this.starter() + msg);
    }

    private String starter() {
        return Prefix.instance.prefix;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onCustomLogin(PlayerJoinEvent ev) {
        Player p = ev.getPlayer();
        ev.setJoinMessage(Prefix.instance.prefix + ChatColor.DARK_GRAY + p.getName() + " has joined.");
        ev.getPlayer().playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!PlayerDeath.deathlist.contains(player)) {
            event.setFormat(this.DefaultFormat);
        } else if(PlayerDeath.deathlist.contains(player)) {
            event.setFormat(this.DeadFormat);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onSignChange(SignChangeEvent event) {
        String CHAR = "&";
        if(CHAR.length() == 1) {
            char[] ch = CHAR.toCharArray();

            for(int i = 0; i <= 3; ++i) {
                String line = event.getLine(i);
                line = ChatColor.translateAlternateColorCodes(ch[0], line);
                event.setLine(i, line);
            }

        }
    }

    /** @deprecated */
    @Deprecated
    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        this.sendTitle(player, fadeIn, stay, fadeOut, message, (String)null);
    }

    /** @deprecated */
    @Deprecated
    public void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        this.sendTitle(player, fadeIn, stay, fadeOut, (String)null, message);
    }

    /** @deprecated */
    @Deprecated
    public void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        this.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, (IChatBaseComponent)null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(packetPlayOutTimes);
        IChatBaseComponent titleMain;
        PacketPlayOutTitle packetPlayOutTitle;
        if(subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            titleMain = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }

        if(title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
            packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }

    }
}

