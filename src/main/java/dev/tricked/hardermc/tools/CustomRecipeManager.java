package dev.tricked.hardermc.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class CustomRecipeManager implements Listener {
    private List<CustomRecipe> customRecipes = new ArrayList<>();

    public void addCustomRecipe(CustomRecipe data) {
        Bukkit.removeRecipe(data.key);
        getServer().addRecipe(data.recipe);
        customRecipes.add(data);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        var type = event.getItem().getItemStack().getType();
        for (CustomRecipe recipe : customRecipes) {
            if (recipe.triggerIngredients.contains(type)) {
                event.getPlayer().discoverRecipe(recipe.key);
            }
        }
    }
}

