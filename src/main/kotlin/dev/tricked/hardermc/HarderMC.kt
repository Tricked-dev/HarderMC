package dev.tricked.hardermc

import dev.tricked.hardermc.features.*
import dev.tricked.hardermc.recipes.EnchantmentTableRecipe
import dev.tricked.hardermc.recipes.EyeOfEnderRecipe
import dev.tricked.hardermc.recipes.chainmail.ChainmailBoots
import dev.tricked.hardermc.recipes.chainmail.ChainmailChestplate
import dev.tricked.hardermc.recipes.chainmail.ChainmailHelmet
import dev.tricked.hardermc.recipes.chainmail.ChainmailLeggings
import dev.tricked.hardermc.recipes.diamondarmor.*
import dev.tricked.hardermc.recipes.ironarmor.IronBoots
import dev.tricked.hardermc.recipes.ironarmor.IronChestplate
import dev.tricked.hardermc.recipes.ironarmor.IronHelmet
import dev.tricked.hardermc.recipes.ironarmor.IronLeggings
import dev.tricked.hardermc.features.CustomRecipeManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

public class HarderMC : JavaPlugin(), Listener {
    public var log: Logger = logger;
    override fun onEnable() {
        val customRecipeManager = CustomRecipeManager(this)
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(MendingNerf(this), this)
        pluginManager.registerEvents(NoExplodieBed(this), this)
        pluginManager.registerEvents(ChestLootToBooks(this), this)
        pluginManager.registerEvents(SmallerShulkers(this), this)
        pluginManager.registerEvents(NoTotem(this), this)
        pluginManager.registerEvents(PortalCloser(this), this)
        pluginManager.registerEvents(PVPRegenCooldown(this), this)
        pluginManager.registerEvents(VillagerTradeModifier(this), this)
        pluginManager.registerEvents(NetherMining(this), this)
        pluginManager.registerEvents(MoreElytraDamage(this), this)
        pluginManager.registerEvents(PiglinEnderPearlRemover(this), this)

        Bukkit.getPluginManager().registerEvents(this, this)

        Bukkit.getPluginManager().registerEvents(customRecipeManager, this)
        customRecipeManager.addCustomRecipes(
            ShieldRecipe(),
            EyeOfEnderRecipe(),
            EnchantmentTableRecipe()
        )
        customRecipeManager.addCustomRecipes(
            ChainmailHelmet(),
            ChainmailChestplate(),
            ChainmailLeggings(),
            ChainmailBoots()
        )
        customRecipeManager.addCustomRecipes(
            IronHelmet(),
            IronChestplate(),
            IronLeggings(),
            IronBoots(),
        )
        customRecipeManager.addCustomRecipes(
            DiamondHelmet(),
            DiamondChestplate(),
            DiamondLeggings(),
            DiamondBoots()
        )
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"))
    }
}