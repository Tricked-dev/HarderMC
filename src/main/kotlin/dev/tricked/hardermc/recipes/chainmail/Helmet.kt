/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice

class ChainmailHelmet : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_helmet"),
    Material.CHAINMAIL_HELMET
) {
    init {
        helmet()
        setMaterials(
            RecipeChoice.MaterialChoice(Material.LEATHER, Material.BLAZE_POWDER),
            Material.CHAIN,
            Material.LEATHER_HELMET
        )
    }
}