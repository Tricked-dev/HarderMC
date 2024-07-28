/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice

class ChainmailLeggings : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_leggings"),
    Material.CHAINMAIL_LEGGINGS
) {
    init {
        leggings()
        setMaterials(
            RecipeChoice.MaterialChoice(Material.LEATHER, Material.BLAZE_POWDER),
            Material.CHAIN,
            Material.LEATHER_LEGGINGS
        )
    }
}