/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.recipes.chainmail

import dev.tricked.hardermc.recipes.BaseArmorPieceRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey

class ChainmailHelmet : BaseArmorPieceRecipe(
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