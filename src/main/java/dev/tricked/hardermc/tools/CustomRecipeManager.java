package dev.tricked.hardermc.tools;

import dev.tricked.hardermc.HarderMC;
import dev.tricked.hardermc.utilities.BaseTool;
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

public class CustomRecipeManager extends BaseTool implements Listener {
    private List<CustomRecipe> customRecipes = new ArrayList<>();

    public CustomRecipeManager(@NotNull HarderMC plugin) {
        super(plugin);
    }

    public void addCustomRecipe(CustomRecipe data) {
        Bukkit.removeRecipe(data.key);
        getServer().addRecipe(data.recipe);
        customRecipes.add(data);
    }

    public void addCustomRecipes(CustomRecipe... data) {
        for (CustomRecipe recipe : data) {
            getLog().info("Adding custom recipe: " + recipe.key);
            addCustomRecipe(recipe);
        }
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

