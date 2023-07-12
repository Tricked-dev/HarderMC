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
import org.bukkit.World.Environment
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

@Name("No explodie")
@Description("Things such as a bed and respawn anchor no longer explode when used in the wrong environment, end crystals cant be placed on obsidian")
class NoExplodieBed(mc: HarderMC) : BaseTool(mc) {
    private var disableNetherBed: Boolean by ConfigProperty(plugin, configPrefix, true)
    private var disableEndBed: Boolean by ConfigProperty(plugin, configPrefix, true)

    private var disableOverworldAnchor: Boolean by ConfigProperty(plugin, configPrefix, true)
    private var disableEndAnchor: Boolean by ConfigProperty(plugin, configPrefix, true)

    private var enableObsidianEndCrystal: Boolean by ConfigProperty(plugin, configPrefix, false)

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!enabled) return

        // Check if the player right-click on a block
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val block = event.clickedBlock ?: return
        val environment = event.player.world.environment
        if (environment != Environment.NORMAL && block.type.toString().endsWith("BED")) {
            if (!disableNetherBed && environment == Environment.NETHER) return
            if (!disableEndBed && environment == Environment.THE_END) return
            event.isCancelled = true
        }
        if (event.player.world.environment != Environment.NETHER && block.type == Material.RESPAWN_ANCHOR) {
            if (!disableOverworldAnchor && environment == Environment.NORMAL) return
            if (!disableEndAnchor && environment == Environment.THE_END) return
            event.isCancelled = true
        }
        if (block.type != Material.BEDROCK && !enableObsidianEndCrystal) {
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