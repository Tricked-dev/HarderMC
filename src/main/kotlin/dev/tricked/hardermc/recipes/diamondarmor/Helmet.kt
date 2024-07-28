/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.recipes.diamondarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class DiamondHelmet : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("diamond_helmet"),
    Material.DIAMOND_HELMET
) {
    init {
        helmet()
        setMaterials(
            Material.DIAMOND,
            Material.ECHO_SHARD,
            Material.IRON_HELMET
        )
    }
}