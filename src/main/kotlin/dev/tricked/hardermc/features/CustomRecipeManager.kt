/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityPickupItemEvent

@Name("Custom Recipe Manager")
@Description("Enables the custom recipes and the obtaining of the recipes")
class CustomRecipeManager(plugin: HarderMC) : BaseTool(plugin) {
    private val customRecipes: MutableList<CustomRecipe> = ArrayList()

    val customArmorEnabled: Boolean by ConfigProperty(plugin, configPrefix, true)
    val customEnchantmentTableEnabled: Boolean by ConfigProperty(plugin, configPrefix, true)
    val customShieldEnabled: Boolean by ConfigProperty(plugin, configPrefix, true)
    val helpBookEnabled: Boolean by ConfigProperty(plugin, configPrefix, true)
    val customEyeOfEnderEnabled: Boolean by ConfigProperty(plugin, configPrefix, true)

    private fun addCustomRecipe(data: CustomRecipe) {
        Bukkit.removeRecipe(data.key!!)
        Bukkit.getServer().addRecipe(data.recipe)
        customRecipes.add(data)
    }

    fun addCustomRecipes(vararg data: CustomRecipe) {
        if (!enabled) return

        for (recipe in data) {
            getLog().info("Adding custom recipe: " + recipe.key)
            addCustomRecipe(recipe)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerPickupItem(event: EntityPickupItemEvent) {
        if (!enabled) return

        val type = event.item.itemStack.type
        for (recipe in customRecipes) {
            if (recipe.triggerIngredients.contains(type)) {
                if (event.entity is Player) {
                    (event.entity as Player).discoverRecipe(recipe.key!!)
                }
            }
        }
    }
}
