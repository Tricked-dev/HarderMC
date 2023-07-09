package dev.tricked.hardermc.recipes.ironarmor

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class IronBoots: BaseArmorPieceRecipe(
    NamespacedKey.minecraft("iron_boots"),
    Material.IRON_BOOTS
) {
    init {
        boots()
        setMaterials(
            Material.IRON_INGOT,
            Material.IRON_INGOT,
            Material.CHAINMAIL_BOOTS
        )
    }
}