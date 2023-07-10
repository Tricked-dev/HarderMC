package dev.tricked.hardermc.recipes

import dev.tricked.hardermc.tools.CustomRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

class EnchantmentTableRecipe: CustomRecipe() {
    init {
        val key = NamespacedKey.minecraft("enchanting_table")

        val recipe = ShapedRecipe(key, ItemStack(Material.ENCHANTING_TABLE))

        recipe.shape("GBG", "DOD", "OOO")
            .setIngredient('O', Material.CRYING_OBSIDIAN)
            .setIngredient('D', Material.DIAMOND)
            .setIngredient('G', Material.GHAST_TEAR)
            .setIngredient('B', Material.BOOK)

        init(key, recipe, arrayOf(Material.CRYING_OBSIDIAN, Material.DIAMOND, Material.GHAST_TEAR, Material.BOOK))
    }
}