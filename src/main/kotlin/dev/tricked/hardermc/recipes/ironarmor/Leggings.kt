package dev.tricked.hardermc.recipes.ironarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class IronLeggings: BaseArmorPieceRecipe(
    NamespacedKey.minecraft("iron_leggings"),
    Material.IRON_LEGGINGS
) {
    init {
        leggings()
        setMaterials(
            Material.IRON_INGOT,
            Material.IRON_INGOT,
            Material.CHAINMAIL_LEGGINGS
        )
    }
}