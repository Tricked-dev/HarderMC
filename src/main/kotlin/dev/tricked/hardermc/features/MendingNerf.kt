/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemMendEvent
import java.util.*


class MendingNerf(mc: HarderMC) : BaseTool(mc), Listener {
    private val random = Random()

    @EventHandler
    fun onItemMend(event: PlayerItemMendEvent) {
        val repairAmount = event.repairAmount
        // 10% of the repair amount is given if the repair amount is more than 10
        // otherwise a 10% chance for the repair to continue
        if (repairAmount > 10) {
            val modifiedRepairAmount = repairAmount / 10
            getLog().info("Repair Amount: $repairAmount")
            getLog().info("Repair AmountNew: $modifiedRepairAmount")
            event.repairAmount = modifiedRepairAmount
        } else if (random.nextInt(10) != 5) {
            event.repairAmount = 0
        }
    }
}