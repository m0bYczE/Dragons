package cz.m0bY_czE.dragons.Utilities;

import net.minecraft.server.v1_8_R1.EntityFireworks;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class CustomEntityFirework extends EntityFireworks {
    Player[] players = null;
    boolean gone = false;

    public CustomEntityFirework(World world, Player... p) {
        super(world);
        this.players = p;
        this.a(0.25F, 0.25F);
    }

    public void h() {
        if(!this.gone) {
            if(!this.world.isStatic) {
                this.gone = true;
                if(this.players != null && this.players.length > 0) {
                    Player[] var4 = this.players;
                    int var3 = this.players.length;

                    for(int var2 = 0; var2 < var3; ++var2) {
                        Player player = var4[var2];
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte)17));
                    }

                    this.die();
                    return;
                }

                this.world.broadcastEntityEffect(this, (byte)17);
                this.die();
            }

        }
    }

    public static void spawn(Location location, FireworkEffect effect) {
        try {
            Firework e = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta meta = e.getFireworkMeta();
            meta.addEffect(effect);
            meta.setPower(0);
            e.setFireworkMeta(meta);
            ((CraftFirework)e).getHandle().setInvisible(true);
            ((CraftFirework)e).getHandle().expectedLifespan = 1;
            ((CraftFirework)e).getHandle().spawnIn(((CraftWorld)location.getWorld()).getHandle());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}
