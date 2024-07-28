/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.data.type.EndPortalFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
//TODO: enable after fixing said issues :)
@Name("Portal closer")
@Description("The portal has gone unstable it cant keep up being open for more than 10 minutes")
class PortalCloser(mc: HarderMC) : BaseTool(mc), Listener {
    private var delayTicks: Int by ConfigProperty(plugin, configPrefix, 10 * 60 * 20)

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!enabled) return
        val clickedBlock = event.clickedBlock ?: return
        //TODO make this work after restarts
        if (clickedBlock.type == Material.END_PORTAL_FRAME) {
            //end portal gets created after it is clicked
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                val radius =
                    4 // Radius of 4 blocks around the clicked block this should find any end portal & frame blocks
                val portalsBlocks = HashSet<Block>()
                val portalFrameBlocks = HashSet<Block>()
                for (xOffset in -radius..radius) {
                    for (yOffset in -radius..radius) {
                        for (zOffset in -radius..radius) {
                            val adjacentBlock = clickedBlock.getRelative(xOffset, yOffset, zOffset)
                            if (adjacentBlock.type == Material.END_PORTAL) {
                                getLog().info("Adjacent End Portal at offset ($xOffset, $yOffset, $zOffset)")
                                portalsBlocks.add(adjacentBlock)
                            } else if (adjacentBlock.type == Material.END_PORTAL_FRAME) {
                                getLog().info("Adjacent End Portal Frame at offset ($xOffset, $yOffset, $zOffset)")
                                portalFrameBlocks.add(adjacentBlock)
                            }
                        }
                    }
                }
                getLog().info("Found " + portalsBlocks.size + " end portal blocks.")
                val world = event.player.world
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    if (portalsBlocks.isNotEmpty()) {
                        val speed = 0.0
                        val particleCount = 400
                        val particleSizeX = 0.3
                        val particleSizeY = 0.3
                        val particleSizeZ = 0.3
                        for (portalBlock in portalsBlocks) {
                            val centerX = portalBlock.x
                            val centerY = portalBlock.y
                            val centerZ = portalBlock.z
                            getLog().info("Portal coordinates: ($centerX, $centerY, $centerZ)")
                            val loc =
                                Location(world, centerX.toDouble(), centerY.toDouble(), centerZ.toDouble())
                            world.spawnParticle(
                                Particle.PORTAL,
                                loc.x + 0.5,
                                loc.y + 0.2,
                                loc.z + 0.5,  // Adjust the coordinates to be at the center of the block
                                particleCount,
                                particleSizeX,
                                particleSizeY,
                                particleSizeZ,
                                speed
                            )
                            portalBlock.type = Material.AIR
                        }
                        for (portalFrameBlock in portalFrameBlocks) {
                            val centerX = portalFrameBlock.x
                            val centerY = portalFrameBlock.y
                            val centerZ = portalFrameBlock.z
                            getLog().info("Portal Frame coordinates: ($centerX, $centerY, $centerZ)")
                            val loc =
                                Location(world, centerX.toDouble(), centerY.toDouble(), centerZ.toDouble())
                            world.spawnParticle(
                                Particle.SPELL_WITCH,
                                loc.x + 0.5, loc.y + 0.2, loc.z + 0.5,
                                30,
                                particleSizeX, particleSizeY, particleSizeZ,
                                speed
                            )
                            val endPortalFrame = portalFrameBlock.blockData as EndPortalFrame
                            endPortalFrame.setEye(false)
                            portalFrameBlock.blockData = endPortalFrame
                            world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f)
                        }
                    } else {
                        getLog().info("No adjacent end portals found.")
                    }
                }, delayTicks.toLong())
            }, 2) // Delay of 2 ticks (20ms per tick) - adjust this value as needed
        }
    }
}