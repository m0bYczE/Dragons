package cz.m0bY_czE.dragons.Handlers;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.GameState;
import cz.m0bY_czE.dragons.Main;
import cz.m0bY_czE.dragons.Listeners.Player.PlayerDeath;
import cz.m0bY_czE.dragons.Threads.Announcer;
import cz.m0bY_czE.dragons.Threads.EndingTasks;
import cz.m0bY_czE.dragons.Utilities.InventoryUtilities;
import cz.m0bY_czE.dragons.Utilities.LocationUtilities;

public class Game {
    public static Game instance;
    private boolean canStart = false;
    private boolean hasStarted = false;
    public static ArrayList<String> players = new ArrayList();

    public Game() {
        instance = this;
    }

    public void start() {
        this.hasStarted = true;
        PlayerDeath.aliveplayers = Bukkit.getOnlinePlayers().size();
        GameState.setState(GameState.PRE_GAME);
        Iterator var2 = Bukkit.getOnlinePlayers().iterator();

        while(var2.hasNext()) {
            Player p = (Player)var2.next();
            Scoreboard.instance.arenaChange(p);
            EndingTasks.instance.pauseTask();
            EndingTasks.instance.infoTask(p);
            p.getInventory().setHeldItemSlot(0);
            InventoryUtilities.instance.clearInventory(p);
            LocationUtilities.instance.nextSpawnLocation(p);
        }

        EndingTasks.instance.delayTask1();
        EndingTasks.instance.delayTask2();
        EndingTasks.instance.delayTask3();
        EndingTasks.instance.delayTask4();
        Bukkit.getScheduler().cancelTask(Main.instance.startCountdownId);
        Bukkit.getScheduler().cancelTask(Announcer.instance.ruleTask);
        Main.instance.removeEntities(Bukkit.getServer().getWorld(Main.instance.getConfig().getString("lobby-world")));
        this.addOnlinePlayers();
    }

    public void stop() {
        this.hasStarted = false;
    }

    public boolean canStart() {
        return this.canStart;
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public void setCanStart(boolean b) {
        this.canStart = b;
    }

    public void addPlayer(Player player) {
        players.add(player.getName());
    }

    public void addOnlinePlayers() {
        Iterator var2 = Bukkit.getServer().getOnlinePlayers().iterator();

        while(var2.hasNext()) {
            Player player = (Player)var2.next();
            this.addPlayer(player);
        }

    }

    public void removePlayer(Player player) {
        players.remove(player.getName());
    }

    public ArrayList<String> getAlivePlayers() {
        return players;
    }
}
