package dev.tricked.hardermc.features;

import dev.tricked.hardermc.HarderMC;
import dev.tricked.hardermc.utilities.BaseTool;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class HarderRecipes extends BaseTool {
    public HarderRecipes(@NotNull HarderMC plugin) {
        super(plugin);
    }

    public void AddRecipes() {
        addCustomEnchanttableRecipe();
    }

    public void addCustomEnchanttableRecipe() {
        Bukkit.removeRecipe(NamespacedKey.minecraft("enchantment_table"));

        // Create a custom recipe key
        NamespacedKey recipeKey = NamespacedKey.minecraft("enchantment_table");

        // Create the modified recipe
        ItemStack enchantmentTable = new ItemStack(Material.ENCHANTING_TABLE);
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, enchantmentTable);

        // Define the modified recipe pattern
        recipe.shape("GBG", "DOD", "OOO");

        // Set the modified recipe ingredients
        recipe.setIngredient('O', Material.CRYING_OBSIDIAN);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('G', Material.GHAST_TEAR);
        recipe.setIngredient('B', Material.BOOK);

        // Add the modified recipe to the server
        getServer().addRecipe(recipe);
    }
}
