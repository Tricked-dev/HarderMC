package dev.tricked.hardermc;

import dev.tricked.hardermc.end.EndEventHandler;
import dev.tricked.hardermc.end.EndRecipeHandler;
import dev.tricked.hardermc.features.HarderRecipes;
import dev.tricked.hardermc.features.HarderShieldRecipe;
import dev.tricked.hardermc.nether.NetherEventHandler;
import dev.tricked.hardermc.overworld.OverworldEventHandler;
import dev.tricked.hardermc.tools.CustomRecipeManager;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ShapelessRecipe;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class HarderMC extends JavaPlugin implements Listener {
    public static Logger log;
    private static HarderMC instance;

    public static HarderMC getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        var customRecipeManager = new CustomRecipeManager();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new EndEventHandler(), this);
        Bukkit.getPluginManager().registerEvents(new NetherEventHandler(), this);
        Bukkit.getPluginManager().registerEvents(new OverworldEventHandler(), this);
        Bukkit.getPluginManager().registerEvents(customRecipeManager, this);
        log = getLogger();
        instance = this;
        customRecipeManager.addCustomRecipe(
                new HarderShieldRecipe()
        );
        new EndRecipeHandler().ModifyRecipes();
        new HarderRecipes().AddRecipes();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }
}