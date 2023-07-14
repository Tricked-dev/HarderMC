/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.inventory.meta.Damageable

@Name("Nether Mining")
@Description("Makes pickaxes take a lot more damage in the nether when below 30")
class NetherMining(mc: HarderMC) : BaseTool(mc) {
    private var factor: Int by ConfigProperty(plugin, configPrefix, 5)
    private var depth: Int by ConfigProperty(plugin, configPrefix, 30)

    @EventHandler
    fun onPlayerItemDamage(event: PlayerItemDamageEvent) {
        if (!enabled) return
        val player = event.player
        if (player.world.environment != World.Environment.NETHER) return
        if (player.location.y() > depth) return
        if (!event.item.type.toString().contains("PICKAXE")) return

        val damage = event.damage * factor
        val itemMeta = event.item.itemMeta

        if (itemMeta is Damageable && itemMeta.damage + damage >= event.item.type.maxDurability) {
            itemMeta.damage = event.item.type.maxDurability - 1
            event.item.setItemMeta(itemMeta)
            event.isCancelled = true
        } else {
            event.damage = damage
        }
    }
}