/*
 * Copyright (c) Tricked-dev 2024.
 */

package dev.tricked.hardermc.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Villager
import org.bukkit.entity.ZombieVillager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.HIGHEST
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTransformEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.inventory.meta.Damageable
import java.util.*

@Name("Villager Trade Modifier")
@Description("Villagers dont sell armor anymore and tools are pretty damaged, trades that give emeralds are limited to 4 uses and shields require 1 scute")
class VillagerTradeModifier(mc: HarderMC) : BaseTool(mc), Listener {
    @EventHandler(priority = HIGHEST)
    fun onPlayerInteract(event: PlayerInteractEntityEvent) {
        if (event.isCancelled) return
        if (!enabled) return
        val entity = event.rightClicked
        if (entity.type != EntityType.VILLAGER) return

        val villager = event.rightClicked as Villager
        // make the recipes mutable
        val recipes = villager.recipes.toMutableList()


        // Modify existing armor trades
        for (recipe in villager.recipes) {
            getLog().info("Checking recipe: $recipe discount " + recipe.priceMultiplier)
            getLog().info("HI: " + recipe.priceMultiplier)
            if (recipe.priceMultiplier > 0.2f) {
                recipe.priceMultiplier = 0.2f
            }

            val resultItem = recipe.result
            if (resultItem.type.toString().endsWith("_HELMET") ||
                resultItem.type.toString().endsWith("_CHESTPLATE") ||
                resultItem.type.toString().endsWith("_LEGGINGS") ||
                resultItem.type.toString().endsWith("_BOOTS")
            ) {
                getLog().info("Removing armor trades")
                // Remove armor trades
                recipes.remove(recipe)
            }

            // Modify existing tool trades
            if (resultItem.type.toString().endsWith("_AXE") ||
                resultItem.type.toString().endsWith("_PICKAXE") ||
                resultItem.type.toString().endsWith("_SHOVEL") ||
                resultItem.type.toString().endsWith("_HOE") ||
                resultItem.type.toString().endsWith("_SWORD")
            ) {
                getLog().info("Editing tool trades")
                // Modify tool durability
                val durability = resultItem.type.maxDurability
                val random = Random()
                val modifiedDurability = (durability - random.nextInt((durability * 0.5f).toInt())) + 5

                // Update the durability of the resulting tool in the recipe
                val itemMeta = resultItem.itemMeta
                if (itemMeta is Damageable && itemMeta.damage == 0) {
                    itemMeta.damage = modifiedDurability
                    resultItem.itemMeta = itemMeta
                }

                val customTrade = MerchantRecipe(resultItem, recipe.maxUses)
                customTrade.priceMultiplier = recipe.priceMultiplier
                customTrade.ingredients = recipe.ingredients
                customTrade.villagerExperience = recipe.villagerExperience;
                customTrade.uses = recipe.uses

                recipes[recipes.indexOf(recipe)] = customTrade
            }
            if (resultItem.type == Material.SHIELD) {
                getLog().info("Editing shield trades")
                recipe.priceMultiplier = 0f
                val ingredients = recipe.ingredients.toMutableList()

                ingredients[1] = ItemStack(Material.SCUTE, 1)
                recipe.ingredients = ingredients
                recipe.setIgnoreDiscounts(true)
            }
            if (resultItem.type == Material.EMERALD) {
                getLog().info("Editing emerald trades")
                if (recipe.maxUses > 4) {
                    recipe.maxUses = 4
                }
            }

            if (resultItem.type == Material.ENDER_PEARL) {
                getLog().info("Editing ender pearl trades")
                recipe.ingredients[0].amount = 32
                if (recipe.maxUses > 2) {
                    recipe.maxUses = 2
                }
            }
            if (recipe.ingredients[0]?.type == Material.STICK) {
                getLog().info("Editing stick trades")
                recipe.ingredients[0].amount = 64
            }
        }
        villager.recipes = recipes
    }

    private val random: Random = Random()

    @EventHandler
    fun onEntityTransform(event: EntityTransformEvent) {
        if (!enabled) return
        if (event.transformReason == EntityTransformEvent.TransformReason.CURED) {
            if (event.transformedEntity.type == EntityType.VILLAGER) {
                // a 1 in 7 chance for curing to fail
                if (random.nextInt(7) != 3) return
                event.isCancelled = true
                val v = event.entity as ZombieVillager
                v.health = 0.0
            }
        }
    }
}