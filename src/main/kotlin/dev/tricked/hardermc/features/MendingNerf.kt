/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemMendEvent
import java.util.*

@Name("Mending Nerf")
@Description("Makes mending less effective")
class MendingNerf(mc: HarderMC) : BaseTool(mc), Listener {
    private val random = Random()

    private var factor: Double by ConfigProperty(plugin, configPrefix, 10.0)

    @EventHandler
    fun onItemMend(event: PlayerItemMendEvent) {
        if (!enabled) return

        val repairAmount = event.repairAmount
        // 10% of the repair amount is given if the repair amount is more than 10
        // otherwise a 10% chance for the repair to continue
        if (repairAmount > factor) {
            val modifiedRepairAmount = repairAmount / factor
            getLog().info("Repair Amount: $repairAmount")
            getLog().info("Repair AmountNew: $modifiedRepairAmount")
            event.repairAmount = modifiedRepairAmount.toInt()
        } else if (random.nextInt(factor.toInt()) != 1) {
            event.repairAmount = 0
        }
    }
}