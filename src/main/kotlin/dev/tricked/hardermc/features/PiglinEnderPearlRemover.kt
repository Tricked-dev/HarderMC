/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PiglinBarterEvent
import org.bukkit.inventory.ItemStack
import java.util.*

@Name("Piglin Ender Pearl Remover")
@Description("Piglins have no interest in giving you ender pearls for your devalued gold")
class PiglinEnderPearlRemover(mc: HarderMC) : BaseTool(mc) {
    @EventHandler
    fun onPiglinBarter(event: PiglinBarterEvent) {
        if (!enabled) return

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