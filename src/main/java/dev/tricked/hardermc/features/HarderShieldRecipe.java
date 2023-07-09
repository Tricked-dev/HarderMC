package dev.tricked.hardermc.features;

import dev.tricked.hardermc.HarderMC;
import dev.tricked.hardermc.tools.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class HarderShieldRecipe extends CustomRecipe {
    public HarderShieldRecipe() {
        NamespacedKey recipeKey = NamespacedKey.minecraft("shield");

        // Create the modified recipe
        ItemStack enchantmentTable = new ItemStack(Material.SHIELD);
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, enchantmentTable);

        // Define the modified recipe pattern
        recipe.shape("ISI", "ISI", " W ");

        RecipeChoice woodChoice = new RecipeChoice.MaterialChoice(
                Material.OAK_LOG,
                Material.SPRUCE_LOG,
                Material.BIRCH_LOG,
                Material.JUNGLE_LOG,
                Material.ACACIA_LOG,
                Material.DARK_OAK_LOG,
                Material.CHERRY_LOG,
                Material.MANGROVE_LOG
        );
        // Set the modified recipe ingredients
        recipe.setIngredient('S', Material.SCUTE);
        recipe.setIngredient('W', woodChoice);
        recipe.setIngredient('I', Material.IRON_INGOT);

        init(recipeKey, recipe, new Material[] { Material.SCUTE, Material.IRON_INGOT });
    }
}
