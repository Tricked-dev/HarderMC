package dev.tricked.hardermc.recipes.diamondarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class DiamondLeggings : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("diamond_leggings"),
    Material.DIAMOND_LEGGINGS
) {
    init {
        leggings()
        setMaterials(
            Material.DIAMOND_LEGGINGS,
            Material.ECHO_SHARD,
            Material.IRON_LEGGINGS
        )
    }
}