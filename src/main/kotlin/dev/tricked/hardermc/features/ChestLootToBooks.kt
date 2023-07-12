/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.*
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.inventory.ItemStack

@Name("Chest Loot To Books")
@Description("Turns all armor pieces into books with their respective enchantments or removes the item from the chest if its not enchanted")
class ChestLootToBooks(mc: HarderMC) : BaseTool(mc) {
    @EventHandler
    fun onLootGenerate(event: LootGenerateEvent) {
        if (!enabled) return
        if (event.loot.isEmpty()) return
        
        val loot = event.loot
        val lootCopy = ArrayList(loot)
        for (item in lootCopy) {
            val type = item.type.toString()
            if (type.contains("LEGGING") || type.contains("BOOTS") || type.contains("CHESTPLATE") || type.contains("HELMET")) {
                val itemMeta = item.itemMeta

                // Check if the item has enchantments
                if (itemMeta.hasEnchants()) {
                    // Create an enchanted book with the same enchantments
                    val enchantedBook = ItemStack(Material.ENCHANTED_BOOK)
                    val bookMeta = enchantedBook.itemMeta

                    // Copy the enchantments from the original item to the enchanted book
                    for (enchant in itemMeta.enchants) {
                        bookMeta.addEnchant(enchant.key, enchant.value, false)
                    }

                    enchantedBook.itemMeta = bookMeta

                    // Replace the original item with the enchanted book
                    loot.remove(item)
                    loot.add(enchantedBook)
                } else {
                    // Remove the item if it has no enchantments
                    loot.remove(item)
                }
            }
        }
    }
}