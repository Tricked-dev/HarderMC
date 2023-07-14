/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


@Name("Enchantment limiter")
@Description("Limits the levels of some enchants!")
class LimitEnchantments(mc: HarderMC) : BaseTool(mc) {
    private var limits: Map<Enchantment, Int> = mapOf(
        Enchantment.DAMAGE_ALL to 4,
        Enchantment.ARROW_DAMAGE to 2,
        Enchantment.PROTECTION_ENVIRONMENTAL to 2,
        Enchantment.PROTECTION_PROJECTILE to 3,
        Enchantment.PROTECTION_EXPLOSIONS to 3,
        Enchantment.PROTECTION_FIRE to 3,
        Enchantment.KNOCKBACK to 0,
        Enchantment.THORNS to 1,
        Enchantment.SWEEPING_EDGE to 2,
    )


    fun limitEnchantment(stack: ItemStack): ItemStack {
        stack.itemMeta = limitEnchantment(stack.itemMeta)
        return stack
    }

    fun limitEnchantment(stack: ItemMeta): ItemMeta {
        for (enchantment in stack.enchants.keys) {
            if (limits.containsKey(enchantment)) {
                if (stack.enchants[enchantment]!! > limits[enchantment]!!) {
                    stack.addEnchant(
                        enchantment,
                        limits[enchantment]!!,
                        false
                    )
                }
            }
        }
        return stack
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerPickupItem(event: EntityPickupItemEvent) {
        if (!enabled) return

        val item = event.item
        val stack = item.itemStack
        if (stack.enchantments.isNotEmpty())
            item.itemStack = limitEnchantment(stack)
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEnchantItem(event: EnchantItemEvent) {
        if (!enabled) return

        val stack = event.item
        if (stack.enchantments.isNotEmpty()) {
            val limitedStack = limitEnchantment(stack)
            stack.itemMeta = limitedStack.itemMeta
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent?) {
        if (!enabled) return

        if (event?.cursor?.enchantments?.isNotEmpty() == true) {
            event.cursor!!.itemMeta = limitEnchantment(event.cursor!!.itemMeta)
        }
    }

    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent) {
        if (!enabled) return

        event.newItems.forEach() {
            if (it.value.itemMeta.enchants.isNotEmpty()) {
                it.value.itemMeta = limitEnchantment(it.value.itemMeta)
            }
        }
    }

    @EventHandler
    fun onInventoryMoveItem(event: InventoryMoveItemEvent) {
        if (!enabled) return

        if (event.item.enchantments.isNotEmpty()) {
            event.item.itemMeta = limitEnchantment(event.item.itemMeta)
        }
    }
}