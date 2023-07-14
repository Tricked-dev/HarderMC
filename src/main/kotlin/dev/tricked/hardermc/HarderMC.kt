/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc

import dev.tricked.hardermc.features.*
import dev.tricked.hardermc.recipes.EnchantmentTableRecipe
import dev.tricked.hardermc.recipes.EyeOfEnderRecipe
import dev.tricked.hardermc.recipes.HelpBookRecipe
import dev.tricked.hardermc.recipes.ShieldRecipe
import dev.tricked.hardermc.recipes.chainmail.ChainmailBoots
import dev.tricked.hardermc.recipes.chainmail.ChainmailChestplate
import dev.tricked.hardermc.recipes.chainmail.ChainmailHelmet
import dev.tricked.hardermc.recipes.chainmail.ChainmailLeggings
import dev.tricked.hardermc.recipes.diamondarmor.DiamondBoots
import dev.tricked.hardermc.recipes.diamondarmor.DiamondChestplate
import dev.tricked.hardermc.recipes.diamondarmor.DiamondHelmet
import dev.tricked.hardermc.recipes.diamondarmor.DiamondLeggings
import dev.tricked.hardermc.recipes.ironarmor.IronBoots
import dev.tricked.hardermc.recipes.ironarmor.IronChestplate
import dev.tricked.hardermc.recipes.ironarmor.IronHelmet
import dev.tricked.hardermc.recipes.ironarmor.IronLeggings
import dev.tricked.hardermc.server.ServerMain
import dev.tricked.hardermc.utilities.BaseTool
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class HarderMC : JavaPlugin(), Listener {
    private var log: Logger = logger
    var enchantLimiter: LimitEnchantments? = null
    override fun onEnable() {
        val customRecipeManager = CustomRecipeManager(this)
        enchantLimiter = LimitEnchantments(this)
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
        pluginManager.registerEvents(MoreHorseLeather(this), this)
        pluginManager.registerEvents(enchantLimiter!!, this)
        pluginManager.registerEvents(customRecipeManager, this)
        pluginManager.registerEvents(this, this)


        customRecipeManager.addCustomRecipes(
            *listOfNotNull(
                if (customRecipeManager.customShieldEnabled) ShieldRecipe() else null,
                if (customRecipeManager.customEyeOfEnderEnabled) EyeOfEnderRecipe() else null,
                if (customRecipeManager.customEnchantmentTableEnabled) EnchantmentTableRecipe() else null,
                if (customRecipeManager.helpBookEnabled) HelpBookRecipe() else null
            ).toTypedArray()
        )
        if (customRecipeManager.customArmorEnabled) customRecipeManager.addCustomRecipes(
            ChainmailHelmet(),
            ChainmailChestplate(),
            ChainmailLeggings(),
            ChainmailBoots()
        )
        if (customRecipeManager.customArmorEnabled) customRecipeManager.addCustomRecipes(
            IronHelmet(),
            IronChestplate(),
            IronLeggings(),
            IronBoots(),
        )
        if (customRecipeManager.customArmorEnabled) customRecipeManager.addCustomRecipes(
            DiamondHelmet(),
            DiamondChestplate(),
            DiamondLeggings(),
            DiamondBoots()
        )
        ServerMain(this)
        BaseTool.instances.forEach { instance ->
            log.info("${instance.javaClass.simpleName} enabled: ${instance.enabled}")

            val fields = ArrayList<String>()

            instance.javaClass.declaredFields.forEach { field ->
                field.isAccessible = true
                if (field.name.contains("\$delegate")) {
                    fields.add(field.name.replace("\$delegate", ""))
                }
            }

            instance::class.memberProperties.forEach { prop ->
                if (fields.any { that -> prop.name.contains(that) }) {
                    prop.isAccessible = true
                    //forces it to set the default value
                    prop.getter.call(instance)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.sendMessage(Component.text("Hello, " + event.player.name + "!"))
    }
}

fun main() {
    println("You are supposed to put the jar in the plugins folder dummy!")
}