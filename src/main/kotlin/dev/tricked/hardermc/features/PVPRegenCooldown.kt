/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason

@Name("PVP Regen Cooldown")
@Description("Getting hit or hitting another player will now make you unable to regen health from eating food and potions")
class PVPRegenCooldown(mc: HarderMC) : BaseTool(mc), Listener {
    private val pvpCooldown = HashMap<Player, Long>()
    private var cooldownSeconds: Int by ConfigProperty(plugin, configPrefix, 120)

    @EventHandler
    fun onPlayerDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player && event.entity is Player) {
            val attacker = event.damager as Player
            val victim = event.entity as Player

            // Disable regeneration for the attacker for 2 minutes
            disableRegeneration(attacker)
            disableRegeneration(victim)
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

    private fun disableRegeneration(player: Player) {
        pvpCooldown[player] = System.currentTimeMillis() + cooldownSeconds * 1000
    }
}