/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.recipes

import dev.tricked.hardermc.utilities.CustomRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.RecipeChoice.MaterialChoice
import org.bukkit.inventory.ShapedRecipe

class ShieldRecipe : CustomRecipe() {
    init {
        val recipeKey = NamespacedKey.minecraft("shield")

        val recipe = ShapedRecipe(recipeKey, ItemStack(Material.SHIELD))

        recipe.shape("ISI", "ISI", " W ")
        val woodChoice: RecipeChoice = MaterialChoice(
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.CHERRY_LOG,
            Material.MANGROVE_LOG
        )

        recipe.setIngredient('S', Material.SCUTE)
        recipe.setIngredient('W', woodChoice)
        recipe.setIngredient('I', Material.IRON_INGOT)

        init(recipeKey, recipe, arrayOf(Material.SCUTE, Material.IRON_INGOT))
    }
}
