/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.server.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class CustomSpawnLogic(mc: HarderMC) : BaseTool(mc), Listener {
    init {
        getLog().info("Custom spawn logic has been enabled.")
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            generateSpawnPoints()
        }, 50)
    }

    private val SPAWN_RADIUS = 128
    private val SPAWN_DISTANCE = 1000
    private val NUM_SPAWN_POINTS = 8
    private val random: Random = Random()

    private fun generateSpawnPoints() {
        // Clear previous spawn points
        for (i in 0 until NUM_SPAWN_POINTS) {
            val spawnKey = "spawn$i"
            if (plugin.getConfig().contains(spawnKey)) {
                plugin.getConfig().set(spawnKey, null)
            }
        }


        // Generate new spawn points
        for (i in 0 until NUM_SPAWN_POINTS) {
            val angle = i * (360.0 / NUM_SPAWN_POINTS)
            val radians = Math.toRadians(angle)
            val x = SPAWN_DISTANCE * cos(radians)
            val z = SPAWN_DISTANCE * sin(radians)

            // Store spawn point in the config
            val spawnKey = "spawn$i"
            plugin.config.set("$spawnKey.x", x.toInt())
            plugin.config.set("$spawnKey.z", z.toInt())
            getLog().info("Spawn point $i has been generated.")
            getLog().info("$spawnKey.x: $x $spawnKey.z: $z")
        }
        plugin.saveConfig()
        plugin.logger.info("Spawn points have been generated.")
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        if (event.isBedSpawn || event.isAnchorSpawn) {
            // Player has a bed, so use the default respawn location
            return;
        }

        val player = event.player
        val spawnLocation: Location = getRandomSpawnLocation()
        player.teleport(spawnLocation)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!player.hasPlayedBefore()) {
            val spawnLocation: Location = getRandomSpawnLocation()
            player.teleport(spawnLocation)
        }
    }

    private fun getRandomSpawnLocation(): Location {
        val spawnIndex: Int = random.nextInt(NUM_SPAWN_POINTS)
        val spawnKey = "spawn$spawnIndex"
        if (plugin.getConfig().contains(spawnKey)) {
            val world: World = getServer().getWorlds().get(0)
            val x: Double = plugin.getConfig().getDouble("$spawnKey.x")
            val z: Double = plugin.getConfig().getDouble("$spawnKey.z")

            val spawnLocation = Location(world, x, 0.0, z)

            // Add random offset within the spawn radius
            val offset: Double = random.nextDouble() * SPAWN_RADIUS
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
        return getServer().getWorlds().get(0).getSpawnLocation()
    }
}