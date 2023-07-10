/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PiglinBarterEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class PiglinEnderPearlRemover(mc: HarderMC) : BaseTool(mc), Listener {
    @EventHandler
    fun onPiglinBarter(event: PiglinBarterEvent) {
        val outcome = event.outcome
        // remove ender pearls form the loot table
        for (item in outcome.toList()) {
            if (item.type == Material.ENDER_PEARL) {
                val rand = Random()
                outcome.clear()
                outcome.add(
                    ItemStack(Material.BONE_MEAL, 2 + rand.nextInt(5))
                )
            }
        }
    }
}