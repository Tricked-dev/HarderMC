/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class MoreHorseLeather(mc: HarderMC) : BaseTool(mc), Listener {
    final val random: Random = Random()

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entityType == EntityType.HORSE) {
            event.drops.add(
                ItemStack(Material.LEATHER, 4 + random.nextInt(4))
            )
        }
    }
}