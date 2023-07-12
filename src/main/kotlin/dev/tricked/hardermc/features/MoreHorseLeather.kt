/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import java.util.*

@Name("More Horse Leather")
@Description("Makes horses drop a bunch more leather making it a good animal to farm for leather")
class MoreHorseLeather(mc: HarderMC) : BaseTool(mc) {
    private val random: Random = Random()
    private var extraLeatherCount: Int by ConfigProperty(plugin, configPrefix, 4)
    private var extraLeatherCountRandom: Int by ConfigProperty(plugin, configPrefix, 4)

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (!enabled) return

        if (event.entityType == EntityType.HORSE) {
            event.drops.add(
                ItemStack(Material.LEATHER, extraLeatherCount + random.nextInt(extraLeatherCountRandom))
            )
        }
    }
}