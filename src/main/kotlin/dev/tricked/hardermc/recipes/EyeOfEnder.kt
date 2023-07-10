package dev.tricked.hardermc.recipes

import dev.tricked.hardermc.utilities.CustomRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe

class EyeOfEnderRecipe: CustomRecipe() {
    init {
        val key = NamespacedKey.minecraft("ender_eye");

        val recipe = ShapelessRecipe(key, ItemStack(Material.ENDER_EYE, 1))
            .addIngredient(4, Material.BLAZE_POWDER)
            .addIngredient(Material.ENDER_PEARL)
            .addIngredient(4, Material.BLAZE_POWDER)

        init(
            key,
            recipe,
            arrayOf(
                Material.BLAZE_POWDER,
                Material.ENDER_PEARL
            )
        )
    }
}