package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import dev.tricked.hardermc.tools.CustomRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.RecipeChoice.MaterialChoice
import org.bukkit.inventory.ShapedRecipe

class ChainmailHelmet() : BaseArmorPieceRecipe(
    NamespacedKey.minecraft("chainmail_helmet"),
    Material.CHAINMAIL_HELMET
) {
    init {
        helmet()
        setMaterials(
            Material.LEATHER,
            Material.CHAIN,
            Material.LEATHER_HELMET
        )
    }
}