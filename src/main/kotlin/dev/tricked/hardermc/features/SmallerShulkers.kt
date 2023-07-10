/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getServer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

// TODO: does not work try to find alternative.
class SmallerShulkers(mc: HarderMC) : BaseTool(mc), Listener {
//    @EventHandler
//    fun onInventoryOpedn(event: InventoryOpenEvent) {
//        val inventory = event.inventory
//        if (inventory.type == InventoryType.SHULKER_BOX) {
//
//            // Create a new inventory with 9 slots
//            val newInventory: Inventory = getServer().createInventory(null, 9, "123")
//
//            // Transfer items to the new inventory
//            val contents = inventory.contents
//            for (i in 0 until Math.min(contents.size, newInventory.size)) {
//                if (contents[i] != null) {
//                    newInventory.setItem(i, contents[i])
//                }
//            }
//
//            // Replace the old inventory with the new one
////            event.inventory = newInventory;
//
//            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
//                event.player.openInventory(newInventory)
//                inventory.clear() // Optional: Clear the old inventory to remove any remaining items
//            }, 1)
//
//            // Save the new inventory data
//            inventory.contents = newInventory.contents
//        }
//    }
//
//    @EventHandler
//    fun onInventoryOpen(event: InventoryOpenEvent) {
//        val inventory = event.inventory
//        if (inventory.type == InventoryType.SHULKER_BOX) {
//
//            val newInventory: Inventory = getServer().createInventory(null, 9, "123")
//
//            val contents = inventory.contents
//            for (i in 0 until Math.min(contents.size, newInventory.size)) {
//                if (contents[i] != null) {
//                    newInventory.setItem(i, contents[i])
//                }
//            }
//
//            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
//                event.player.openInventory(newInventory)
//                inventory.clear()
//            }, 1)
//
//            inventory.contents = newInventory.contents
//        }
//    }
}