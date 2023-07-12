/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.server.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

@Name("Custom Spawn Logic")
@Description("Custom spawn logic that makes spawn less bloated and spreads everyone out")
class CustomSpawnLogic(mc: HarderMC) : BaseTool(mc) {
    init {
        getLog().info("Custom spawn logic has been enabled.")
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            generateSpawnPoints()
        }, 50)
    }


    private var spawnRadius: Int by ConfigProperty(plugin, configPrefix, 128)
    private var spawnDistance: Int by ConfigProperty(plugin, configPrefix, 800)
    private var numSpawnPoints: Int by ConfigProperty(plugin, configPrefix, 32)


    private val random: Random = Random()

    private fun generateKey(i: Int): String {
        return "$configPrefix.spawns.${i + 1}"
    }

    private fun generateSpawnPoints() {
        // Clear previous spawn points
        var c = false;
        for (i in 0 until numSpawnPoints) {
            if (!plugin.config.contains(generateKey(i))) {
                c = true;
                break
            }
        }
        if (!c) {
            return
        }


        // Generate new spawn points
        for (i in 0 until numSpawnPoints) {
            val angle = i * (360.0 / numSpawnPoints)
            val radians = Math.toRadians(angle)
            val x = spawnDistance * cos(radians)
            val z = spawnDistance * sin(radians)

            // Store spawn point in the config
            val spawnKey = generateKey(i)
            plugin.config.set("$spawnKey.x", x.toInt())
            plugin.config.set("$spawnKey.z", z.toInt())
            getLog().info("Spawn point $i has been generated.")
            getLog().info("$spawnKey.x: $x $spawnKey.z: $z")
        }
        plugin.saveConfig()
        plugin.logger.info("Spawn points have been generated.")
    }

    // retries a teleport once after 60ms
    private fun safeTeleport(player: Player, location: Location) {
        val r = player.teleport(location)
        if (!r) {
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                player.teleportAsync(location).thenAccept {
                    getLog().info("Async Done?")
                }
            }, 3)
        }
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        if (!enabled) return

        if (event.isBedSpawn || event.isAnchorSpawn) {
            // Player has a bed, so use the default respawn location
            return
        }

        val player = event.player
        val spawnLocation: Location = getRandomSpawnLocation()
        getLog().info("Teleporting player...")
        safeTeleport(player, spawnLocation)
        getLog().info("Teleporting complete")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!enabled) return

        val player = event.player
        if (!player.hasPlayedBefore()) {
            val spawnLocation: Location = getRandomSpawnLocation()
            safeTeleport(player, spawnLocation)
        }
    }

    private fun getRandomSpawnLocation(): Location {
        val spawnIndex: Int = random.nextInt(numSpawnPoints)
        val spawnKey = generateKey(spawnIndex)
        if (plugin.config.contains(spawnKey)) {
            val world: World = getServer().worlds[0]
            val x: Double = plugin.config.getDouble("$spawnKey.x")
            val z: Double = plugin.config.getDouble("$spawnKey.z")

            val spawnLocation = Location(world, x, 0.0, z)

            // Add random offset within the spawn radius
            val offset: Double = random.nextDouble() * spawnRadius
            val angle: Double = random.nextDouble() * 360.0
            val radians = Math.toRadians(angle)
            val offsetX = offset * Math.cos(radians)
            val offsetZ = offset * Math.sin(radians)
            // Apply offset to the spawn location
            spawnLocation.add(offsetX, 0.0, offsetZ)
            spawnLocation.y =
                (world.getHighestBlockYAt(spawnLocation.x.toInt(), spawnLocation.z.toInt()) + 1).toDouble()
            getLog().info("Spawn location: $spawnLocation")
            return spawnLocation
        }

        // Fallback to world spawn if the configured spawn point is missing
        return getServer().worlds[0].spawnLocation
    }
}