package dev.tricked.hardermc.overworld;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Map;
import java.util.Random;

public class OverworldEventHandler implements Listener {
    private Map<Player, Inventory> customInventories;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.EVOKER) {
            var drops = event.getDrops();
            var random = new Random();
            for(var index = 0; index < drops.size(); index++) {
                    if(drops.get(index).getType() == Material.TOTEM_OF_UNDYING) {
                            drops.set(index, new ItemStack(Material.GOLDEN_APPLE, 2 + random.nextInt(3)));
                    }
            }
        }
    }
//    @EventHandler
//    public void onInventoryOpen(InventoryOpenEvent event) {
//        Inventory inventory = event.getInventory();
//        Player player = (Player) event.getPlayer();
//        InventoryHolder holder = event.getInventory().getHolder();
//        if (holder instanceof ShulkerBox shulker) {
//            Inventory customInventory = Bukkit.createInventory(null, 9, "Hi");
//
//            // Copy the items from the original inventory to the custom inventory
//            ItemStack[] contents = inventory.getContents();
//            for (int i = 0; i < Math.min(contents.length, 9); i++) {
//                customInventory.setItem(i, contents[i]);
//            }
//
//            // Open the custom inventory for the player
//            player.openInventory(customInventory);
//
//            // Cancel the event to prevent the original inventory from opening
//            event.setCancelled(true);
//        }
//    }

}
