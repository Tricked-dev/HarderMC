/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.utilities

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.Recipe

abstract class CustomRecipe {
    var key: NamespacedKey? = null
    var recipe: Recipe? = null
    var triggerIngredients: List<Material> = ArrayList()
    fun init(key: NamespacedKey, recipe: Recipe, triggerIngredients: Array<Material>) {
        this.key = key
        this.recipe = recipe
        this.triggerIngredients = triggerIngredients.toList()
    }

    fun setRecipeKey(key: NamespacedKey) {
        this.key = key
    }
}
