/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice

class ChainmailBoots : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_boots"),
    Material.CHAINMAIL_BOOTS
) {
    init {
        boots()
        setMaterials(
            RecipeChoice.MaterialChoice(Material.LEATHER, Material.BLAZE_POWDER),
            Material.CHAIN,
            Material.LEATHER_BOOTS
        )
    }
}