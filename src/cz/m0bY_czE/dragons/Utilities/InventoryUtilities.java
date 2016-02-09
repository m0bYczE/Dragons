package cz.m0bY_czE.dragons.Utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtilities {
    public static InventoryUtilities instance;

    public InventoryUtilities() {
        instance = this;
    }

    public void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents((ItemStack[])null);
        player.updateInventory();
    }
}

