/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import java.util.*

@Name("No Totems")
@Description("Evokers no longer drop totems making totems unobtainable")
class NoTotem(mc: HarderMC) : BaseTool(mc) {
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (!enabled) return

        if (event.entityType == EntityType.EVOKER) {
            val drops = event.drops
            val random = Random()
            for (index in drops.indices) {
                if (drops[index].type == Material.TOTEM_OF_UNDYING) {
                    drops[index] = ItemStack(Material.GOLDEN_APPLE, 2 + random.nextInt(3))
                }
            }
        }
    }
}