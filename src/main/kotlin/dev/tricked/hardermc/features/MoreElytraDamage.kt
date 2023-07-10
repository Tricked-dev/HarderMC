package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.inventory.meta.Damageable

class MoreElytraDamage(mc: HarderMC): BaseTool(mc), Listener {

    @EventHandler
    fun onPlayerItemDamage(event: PlayerItemDamageEvent) {
        // Check if the damaged item is an Elytra
        if (event.item.type == Material.ELYTRA) {
            // Modify the durability usage
            val damage = event.damage * 40
            val itemMeta = event.item.itemMeta
            getLog().info("DMG $damage")
            getLog().info("TOTAL_DMG " + event.item.type.maxDurability)
            if (itemMeta is Damageable && itemMeta.damage + damage >= event.item.type.maxDurability) {
                getLog().info("DMG123 " + itemMeta.damage)
                itemMeta.damage = event.item.type.maxDurability - 1
                event.item.setItemMeta(itemMeta)
                event.isCancelled = true
            } else {
                event.damage = damage
            }
        }
    }
}