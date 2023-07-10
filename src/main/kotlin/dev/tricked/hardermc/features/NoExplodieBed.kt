package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Material
import org.bukkit.World.Environment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent


class NoExplodieBed(mc:HarderMC): BaseTool(mc), Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {

        // Check if the player right-click on a block
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val block = event.clickedBlock ?: return

        if (event.player.world.environment != Environment.NORMAL && block.type.toString().endsWith("BED")) {
            event.isCancelled = true
        }
        if (event.player.world.environment != Environment.NETHER &&  block.type == Material.RESPAWN_ANCHOR) {
            event.isCancelled = true
        }
        if (block.getType() != Material.BEDROCK) {
            val itemInHand = event.player.inventory.itemInMainHand
            if (itemInHand.type == Material.END_CRYSTAL) {
                event.isCancelled = true
            }
            val itemInOffHand = event.player.inventory.itemInOffHand
            if (itemInOffHand.type == Material.END_CRYSTAL) {
                event.isCancelled = true
            }
        }

        //TODO player feedback
    }
}