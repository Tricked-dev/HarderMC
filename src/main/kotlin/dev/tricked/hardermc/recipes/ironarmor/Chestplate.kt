/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.recipes.ironarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class IronChestplate : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("iron_chestplate"),
    Material.IRON_CHESTPLATE
) {
    init {
        chestplate()
        setMaterials(
            Material.IRON_INGOT,
            Material.IRON_INGOT,
            Material.CHAINMAIL_CHESTPLATE
        )
    }
}