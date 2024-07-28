/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.recipes.diamondarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class DiamondChestplate : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("diamond_chestplate"),
    Material.DIAMOND_CHESTPLATE
) {
    init {
        chestplate()
        setMaterials(
            Material.DIAMOND,
            Material.ECHO_SHARD,
            Material.IRON_CHESTPLATE
        )
    }
}