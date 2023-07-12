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
import org.bukkit.World
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import kotlin.math.pow

@Name("Nether Mining")
@Description("Makes pickaxes take a lot more damage in the nether when below 30")
class NetherMining(mc: HarderMC) : BaseTool(mc) {
    private var extraDamage: Int by ConfigProperty(plugin, configPrefix, 5)
    private var depth: Int by ConfigProperty(plugin, configPrefix, 30)

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (!enabled) return

        val player = event.player
        if (event.block.type != Material.NETHERRACK) return
        if (player.world.environment != World.Environment.NETHER) return
        if (player.location.y() > depth) return
        val item = event.player.inventory.itemInMainHand
        if (!item.type.toString().contains("PICKAXE")) return
        if (item.itemMeta is Damageable) {
            val damageable = item.itemMeta as Damageable

            val currentDamage = damageable.damage
            var increase = extraDamage
            val reductionPercentage = 0.1
            val unbreakingLevel = item.getEnchantmentLevel(Enchantment.DURABILITY)
            if (unbreakingLevel > 0) {
                val totalReduction = (1 - reductionPercentage).pow(unbreakingLevel.toDouble())
                increase = (increase * totalReduction).toInt()
            }
            val newDamage = currentDamage + increase
            if (newDamage >= item.type.maxDurability) {
                damageable.damage = item.type.maxDurability.toInt()
                item.setItemMeta(damageable as ItemMeta?)
            } else {
                damageable.damage = newDamage
                item.setItemMeta(damageable as ItemMeta?)
            }
        }
        plugin.log.info("Breaking Nether Block")
        plugin.log.info("Type: " + item.type)
    }
}