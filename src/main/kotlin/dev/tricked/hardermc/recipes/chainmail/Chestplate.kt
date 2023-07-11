/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice

class ChainmailChestplate : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_chestplate"),
    Material.CHAINMAIL_CHESTPLATE
) {
    init {
        chestplate()
        setMaterials(
            RecipeChoice.MaterialChoice(Material.LEATHER, Material.BLAZE_POWDER),
            Material.CHAIN,
            Material.LEATHER_CHESTPLATE
        )
    }
}