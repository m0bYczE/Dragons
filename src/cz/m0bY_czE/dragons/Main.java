package cz.m0bY_czE.dragons;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import cz.m0bY_czE.dragons.Commands.Block;
import cz.m0bY_czE.dragons.Commands.Start;
import cz.m0bY_czE.dragons.Handlers.BlockBreaking;
import cz.m0bY_czE.dragons.Handlers.Blood;
import cz.m0bY_czE.dragons.Handlers.Game;
import cz.m0bY_czE.dragons.Handlers.Prefix;
import cz.m0bY_czE.dragons.Handlers.ResetWorld;
import cz.m0bY_czE.dragons.Handlers.Scoreboard;
import cz.m0bY_czE.dragons.Handlers.TimeControl;
import cz.m0bY_czE.dragons.Listeners.Entity.EntityDamageByEntity;
import cz.m0bY_czE.dragons.Listeners.Entity.Hunger;
import cz.m0bY_czE.dragons.Listeners.Entity.MonsterSpawner;
import cz.m0bY_czE.dragons.Listeners.Entity.Weather;
import cz.m0bY_czE.dragons.Listeners.Inventory.GameEnding;
import cz.m0bY_czE.dragons.Listeners.Inventory.InventoryClick;
import cz.m0bY_czE.dragons.Listeners.Inventory.PlayerInteract;
import cz.m0bY_czE.dragons.Listeners.Player.AsyncPlayerPreLogin;
import cz.m0bY_czE.dragons.Listeners.Player.LobbySelector;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerJoin;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerMoving;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerQuit;
import cz.m0bY_czE.dragons.Threads.Announcer;
import cz.m0bY_czE.dragons.Threads.EndingTasks;
import cz.m0bY_czE.dragons.Threads.ServerListPing;
import cz.m0bY_czE.dragons.Threads.StartCountdown;
import cz.m0bY_czE.dragons.Utilities.ChatUtilities;
import cz.m0bY_czE.dragons.Utilities.InventoryUtilities;
import cz.m0bY_czE.dragons.Utilities.LocationUtilities;

public class Main extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");
    public int startCountdownId;
    public int TimeControl;
    public static Main instance;
    public Economy econ = null;
    Server server = this.getServer();

    public Main() {
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " has been disabled!");
        instance = null;
        Game.instance = null;
        Prefix.instance = null;
        Scoreboard.instance = null;
        PlayerInteract.instance = null;
        EndingTasks.instance = null;
        StartCountdown.instance = null;
        ChatUtilities.instance = null;
        InventoryUtilities.instance = null;
        LocationUtilities.instance = null;
        Announcer.instance = null;
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "npc remove all");
    }

    public void onEnable() {
        ConsoleCommandSender console = this.server.getConsoleSender();
        console.sendMessage("");
        console.sendMessage(ChatColor.AQUA + this.getDescription().getName());
        console.sendMessage(ChatColor.AQUA + "Version " + this.getDescription().getVersion());
        console.sendMessage("");
        instance = this;
        this.saveDefaultConfig();
        ResetWorld.importWorlds();
        Bukkit.getServer().createWorld(new WorldCreator(instance.getConfig().getString("lobby-world")));
        Bukkit.getServer().createWorld(new WorldCreator(instance.getConfig().getString("arena-world")));
        GameState.setState(GameState.IN_LOBBY);
        new Announcer();
        new Game();
        new Prefix();
        new Scoreboard();
        new PlayerInteract();
        new EndingTasks();
        new ChatUtilities();
        new InventoryUtilities();
        new LocationUtilities();
        this.setupEconomy();
        this.startCountdown();
        this.TimeControl();
        this.removeEntities(Bukkit.getServer().getWorld(instance.getConfig().getString("lobby-world")));
        this.removeEntities(Bukkit.getServer().getWorld(instance.getConfig().getString("arena-world")));
        Announcer.instance.showRules();
        this.getCommand("forcestart").setExecutor(new Start(this));
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ServerListPing(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GameEnding(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Scoreboard(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatUtilities(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Hunger(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Weather(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLogin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreaking(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Block(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new LobbySelector(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MonsterSpawner(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Blood(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMoving(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public void removeEntities(World w) {
        Iterator var3 = w.getEntities().iterator();

        while(var3.hasNext()) {
            Entity e = (Entity)var3.next();
            e.remove();
        }

    }

    private boolean setupEconomy() {
        if(this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if(rsp == null) {
                return false;
            } else {
                this.econ = (Economy)rsp.getProvider();
                return this.econ != null;
            }
        }
    }

    public void startCountdown() {
        (new StartCountdown(this)).timeUntilStart = 60;
        this.startCountdownId = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, StartCountdown.instance, 20L, 20L);
    }

    public void TimeControl() {
        this.TimeControl = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeControl(this), 0L, 600L);
    }

    public void stopCountdown() {
        this.getServer().getScheduler().cancelTask(this.startCountdownId);
    }

    public void restartCountdown() {
        this.stopCountdown();
        this.startCountdown();
    }

    public static void switchServer(Player p, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException var5) {
            ;
        }

        p.sendPluginMessage(instance, "BungeeCord", b.toByteArray());
    }
}

