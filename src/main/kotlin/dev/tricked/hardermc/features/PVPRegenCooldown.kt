/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason


class PVPRegenCooldown(mc: HarderMC) : BaseTool(mc), Listener {
    private val pvpCooldown = HashMap<Player, Long>()

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player && event.entity is Player) {
            val attacker = event.damager as Player
            val victim = event.entity as Player

            // Disable regeneration for the attacker for 2 minutes
            disableRegeneration(attacker, 120)
            disableRegeneration(victim, 120)
        }
    }

    @EventHandler
    fun onPlayerRegainHealth(event: EntityRegainHealthEvent) {
        if ((event.regainReason == RegainReason.SATIATED || event.regainReason == RegainReason.REGEN)
            && event.entity is Player
            && pvpCooldown.containsKey(event.entity)
        ) {
            val player = event.entity as Player
            val currentTime = System.currentTimeMillis()
            val cooldownTime = pvpCooldown[player] ?: 0L
            // TODO: add this to tab
            if (currentTime >= cooldownTime) {
                getLog().info("Removed player from regen cooldown")
                // The cooldown has expired, remove the player from the map
                pvpCooldown.remove(player)
            } else {
                getLog().info("Player is still in the regen cooldown, skipping")
                event.isCancelled = true
            }
        }
    }

    private fun disableRegeneration(player: Player, seconds: Int) {
        pvpCooldown[player] = System.currentTimeMillis() + seconds * 1000
    }
}