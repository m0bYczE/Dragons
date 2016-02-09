package cz.m0bY_czE.dragons.Handlers;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SimpleScoreboard {
    public Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private String title;
    private Map<String, Integer> scores;
    private List<Team> teams;

    public SimpleScoreboard(String title) {
        this.title = title;
        this.scores = Maps.newLinkedHashMap();
        this.teams = Lists.newArrayList();
    }

    public void blankLine() {
        this.add(" ");
    }

    public void add(String text) {
        this.add(text, (Integer)null);
    }

    public void add(String text, Integer score) {
        Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");
        text = this.fixDuplicates(text);
        this.scores.put(text, score);
    }

    private String fixDuplicates(String text) {
        while(this.scores.containsKey(text)) {
            text = text + "?r";
        }

        if(text.length() > 48) {
            text = text.substring(0, 47);
        }

        return text;
    }

    private Entry<Team, String> createTeam(String text) {
        String result = "";
        if(text.length() <= 16) {
            return new SimpleEntry((Object)null, text);
        } else {
            Team team = this.scoreboard.registerNewTeam("text-" + this.scoreboard.getTeams().size());
            Iterator iterator = Splitter.fixedLength(16).split(text).iterator();
            team.setPrefix((String)iterator.next());
            result = (String)iterator.next();
            if(text.length() > 32) {
                team.setSuffix((String)iterator.next());
            }

            this.teams.add(team);
            return new SimpleEntry(team, result);
        }
    }

    public void build() {
        Objective obj = this.scoreboard.registerNewObjective(this.title.length() > 16?this.title.substring(0, 15):this.title, "dummy");
        obj.setDisplayName(this.title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        int index = this.scores.size();

        for(Iterator var4 = this.scores.entrySet().iterator(); var4.hasNext(); --index) {
            Entry text = (Entry)var4.next();
            Entry team = this.createTeam((String)text.getKey());
            Integer score = Integer.valueOf(text.getValue() != null?((Integer)text.getValue()).intValue():index);
            Message player = new Message((String)team.getValue());
            if(team.getKey() != null) {
                ((Team)team.getKey()).addPlayer(player);
            }

            obj.getScore(player).setScore(score.intValue());
        }

    }

    public void reset() {
        this.title = null;
        this.scores.clear();
        Iterator var2 = this.teams.iterator();

        while(var2.hasNext()) {
            Team t = (Team)var2.next();
            t.unregister();
        }

        this.teams.clear();
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void send(Player... players) {
        Player[] var5 = players;
        int var4 = players.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Player p = var5[var3];
            p.setScoreboard(this.scoreboard);
        }

    }

    public void resetScoreboard(Player p) {
        SimpleScoreboard scoreboard = new SimpleScoreboard("");
        scoreboard.build();
        scoreboard.send(new Player[]{p});
        scoreboard.reset();
    }
}

