package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class ChainmailBoots: BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_boots"),
    Material.CHAINMAIL_BOOTS
) {
    init {
        boots()
        setMaterials(
            Material.LEATHER,
            Material.CHAIN,
            Material.LEATHER_BOOTS
        )
    }
}