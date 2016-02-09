package cz.m0bY_czE.dragons.Handlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.entity.Player;

import cz.m0bY_czE.dragons.Main;

public class LobbyKick {
    public LobbyKick() {
    }

    public static void doKick(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF("lobby");
        } catch (IOException var4) {
            ;
        }

        player.sendPluginMessage(Main.instance, "BungeeCord", b.toByteArray());
    }
}

