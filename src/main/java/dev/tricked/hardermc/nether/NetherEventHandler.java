package dev.tricked.hardermc.nether;

import dev.tricked.hardermc.HarderMC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class NetherEventHandler implements Listener {

    @EventHandler
    public void onPiglinBarter(PiglinBarterEvent event) {
        var outcome = event.getOutcome();
        //remove ender pearls form the loot table
        var change = false;
        for(var item : outcome) {
            if(item.getType() == Material.ENDER_PEARL) {
                change = true;
            }
        }
        if(change) {
            Random rand = new Random();

            outcome.clear();
            outcome.add(
                    2+rand.nextInt(5),
                    new ItemStack(Material.BONE_MEAL)
            );
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var player = event.getPlayer();
        if (event.getBlock().getType() !=  Material.NETHERRACK) return;
        if(!player.getWorld().getName().equals("world_nether")) return;
        if(player.getLocation().y() > 30) return;
        var item = event.getPlayer().getInventory().getItemInMainHand();
        if(!item.getType().toString().contains("PICKAXE")) return;

        if (item.getItemMeta() instanceof Damageable damageable) {
            int currentDamage = damageable.getDamage();
            var increase = 5;
            double reductionPercentage = 0.1;

            int unbreakingLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
            if (unbreakingLevel > 0) {
                double totalReduction = Math.pow(1 - reductionPercentage, unbreakingLevel);
                increase = (int) (increase * totalReduction);
            }

            int newDamage = currentDamage + increase;

            if (newDamage >= item.getType().getMaxDurability()) {
                damageable.setDamage(item.getType().getMaxDurability());
                item.setItemMeta((ItemMeta) damageable);
                // Pickaxe has reached or exceeded its maximum durability, trigger breaking effect
                PlayerInteractEvent fakeEvent = new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, item, null, null);
                Bukkit.getPluginManager().callEvent(fakeEvent);
            } else {
                damageable.setDamage(newDamage);
                item.setItemMeta((ItemMeta) damageable);
            }
        }
        HarderMC.log.info("Breaking Nether Block");
        HarderMC.log.info("Type: " +  item.getType());
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (event.getItem().getItemStack().getType() == Material.GHAST_TEAR) {
            if (!event.getPlayer().getInventory().contains(Material.ENCHANTING_TABLE)) {
                event.getPlayer().discoverRecipe(NamespacedKey.minecraft("enchantment_table"));
            }
        }
        if (event.getItem().getItemStack().getType() == Material.BLAZE_POWDER) {
            if (!event.getPlayer().getInventory().contains(Material.ENDER_EYE)) {
                event.getPlayer().discoverRecipe(NamespacedKey.minecraft("ender_eye"));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        // Check if the damaged item is an Elytra
        if (event.getItem().getType() == Material.ELYTRA) {
            // Cancel the event to prevent default durability usage

            // Modify the durability usage
            int damage = event.getDamage() * 40;
            ItemMeta itemMeta = event.getItem().getItemMeta();
            HarderMC.log.info("DMG " + damage);
            HarderMC.log.info("TOTAL_DMG " + event.getItem().getType().getMaxDurability());
            if (itemMeta instanceof Damageable damageable && damageable.getDamage() + damage >= event.getItem().getType().getMaxDurability()) {
                HarderMC.log.info("DMG123 " + damageable.getDamage());
                damageable.setDamage(event.getItem().getType().getMaxDurability()-1);
                event.getItem().setItemMeta(itemMeta);
                event.setCancelled(true);
            } else {
                event.setDamage(damage);
            }
        }
    }
}
