/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.recipes.ironarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class IronHelmet : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("iron_helmet"),
    Material.IRON_HELMET
) {
    init {
        helmet()
        setMaterials(
            Material.IRON_INGOT,
            Material.IRON_INGOT,
            Material.CHAINMAIL_HELMET
        )
    }
}