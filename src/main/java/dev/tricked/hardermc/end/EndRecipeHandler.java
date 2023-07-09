package dev.tricked.hardermc.end;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class EndRecipeHandler {
    public void ModifyRecipes() {
        Bukkit.removeRecipe(NamespacedKey.minecraft("ender_eye"));
        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft("ender_eye"),new ItemStack(Material.ENDER_EYE,1))
                .addIngredient(4, Material.BLAZE_POWDER )
                .addIngredient(Material.ENDER_PEARL)
                .addIngredient(4, Material.BLAZE_POWDER );
        Bukkit.addRecipe(recipe);
    }
}
