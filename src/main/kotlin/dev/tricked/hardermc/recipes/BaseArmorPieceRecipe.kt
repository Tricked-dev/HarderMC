package dev.tricked.hardermc.recipes

import dev.tricked.hardermc.tools.CustomRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

open class BaseArmorPieceRecipe: CustomRecipe {
    val sRecipe: ShapedRecipe
        get() {
            return recipe as ShapedRecipe
        }

    constructor(key:NamespacedKey, item:Material): super() {
        recipe = ShapedRecipe(key, ItemStack(item))
        setKey(key);
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

        this.triggerIngredients = ArrayList();
        this.triggerIngredients.add(l);
        this.triggerIngredients.add(c);
        this.triggerIngredients.add(h);
        return this
    }
}