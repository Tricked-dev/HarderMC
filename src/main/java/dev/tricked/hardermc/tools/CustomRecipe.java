package dev.tricked.hardermc.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

abstract public class CustomRecipe {
    public @NotNull NamespacedKey key;
    public @NotNull Recipe recipe;
    public @NotNull List<Material> triggerIngredients;

    public void init(@NotNull NamespacedKey key, @NotNull Recipe recipe, @NotNull Material[] triggerIngredients) {
        this.key = key;
        this.recipe = recipe;
        this.triggerIngredients = Arrays.stream(triggerIngredients).toList();
    }

}
