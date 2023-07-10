/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.CustomRecipe
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

class CustomRecipeManager(plugin: HarderMC) : BaseTool(plugin), Listener {
    private val customRecipes: MutableList<CustomRecipe> = ArrayList()
    private fun addCustomRecipe(data: CustomRecipe) {
        Bukkit.removeRecipe(data.key!!)
        Bukkit.getServer().addRecipe(data.recipe)
        customRecipes.add(data)
    }

    fun addCustomRecipes(vararg data: CustomRecipe) {
        for (recipe in data) {
            getLog().info("Adding custom recipe: " + recipe.key)
            addCustomRecipe(recipe)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerPickupItem(event: PlayerPickupItemEvent) {
        val type = event.item.itemStack.type
        for (recipe in customRecipes) {
            if (recipe.triggerIngredients!!.contains(type)) {
                event.player.discoverRecipe(recipe.key!!)
            }
        }
    }
}
