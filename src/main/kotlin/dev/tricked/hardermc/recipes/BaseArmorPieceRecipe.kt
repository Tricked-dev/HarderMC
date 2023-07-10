package dev.tricked.hardermc.recipes

import dev.tricked.hardermc.utilities.CustomRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import java.util.ArrayList

open class BaseArmorPieceRecipe(key: NamespacedKey, item: Material) : CustomRecipe() {
    init {
        recipe = ShapedRecipe(key, ItemStack(item))
        this.setRecipeKey(key)
    }

    private val sRecipe: ShapedRecipe
        get() {
            return recipe as ShapedRecipe
        }

    protected fun boots(): BaseArmorPieceRecipe {
        sRecipe.shape("   ", "CHC", "L L")
        return this;
    }
    protected fun helmet(): BaseArmorPieceRecipe {
        sRecipe.shape("LLL", "CHC", "   ")
        return this;
    }
    protected fun chestplate(): BaseArmorPieceRecipe {
        sRecipe.shape("LHL", "CLC", "LLL")
        return this;
    }
    protected fun leggings() : BaseArmorPieceRecipe{
        sRecipe.shape("CLC", "LHL", "L L")
        return this;
    }
    protected fun setMaterials(l: Material, c:Material, h:Material): BaseArmorPieceRecipe {
        sRecipe.setIngredient('L', l)
        sRecipe.setIngredient('C', c)
        sRecipe.setIngredient('H', h)

        triggerIngredients = arrayOf(l, c, h).toList();
        return this
    }
}