/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.recipes.diamondarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class DiamondBoots : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("diamond_boots"),
    Material.DIAMOND_BOOTS
) {
    init {
        boots()
        setMaterials(
            Material.DIAMOND,
            Material.ECHO_SHARD,
            Material.IRON_BOOTS
        )
    }
}