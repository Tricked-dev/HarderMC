package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import kotlin.math.pow

class NetherMining(mc: HarderMC): BaseTool(mc), Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        if (event.block.type != Material.NETHERRACK) return
        if (player.world.environment != World.Environment.NETHER) return
        if (player.location.y() > 30) return
        val item = event.player.inventory.itemInMainHand
        if (!item.type.toString().contains("PICKAXE")) return
        if (item.itemMeta is Damageable) {
            val damageable = item.itemMeta as Damageable;

            val currentDamage = damageable.damage
            var increase = 5
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