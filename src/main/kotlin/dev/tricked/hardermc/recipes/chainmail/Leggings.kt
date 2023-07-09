package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class ChainmailLeggings: BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_leggings"),
    Material.CHAINMAIL_LEGGINGS
) {
    init {
        leggings()
        setMaterials(
            Material.LEATHER,
            Material.CHAIN,
            Material.LEATHER_LEGGINGS
        )
    }
}