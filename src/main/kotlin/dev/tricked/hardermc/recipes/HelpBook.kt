/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.recipes

import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.CustomRecipe
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.meta.BookMeta

class HelpBookRecipe : CustomRecipe() {
    init {
        val recipeKey = NamespacedKey.minecraft("hardermc_book")

        val book = ItemStack(Material.WRITTEN_BOOK)
        val bookMeta = book.itemMeta as BookMeta

        bookMeta.title = "Hi"
        bookMeta.author = "HarderMC"

        val pageResults = ArrayList<ArrayList<BaseTool>>();
        var currentPage = ArrayList<BaseTool>()
        var count = 0;
        val PER_PAGE = 200;
        for (tool in BaseTool.instances) {
            val total = tool.description.length + tool.name.length
            if (total == 0) continue;
            if (total + count > PER_PAGE) {
                pageResults.add(currentPage)
                currentPage = ArrayList()
                count = 0
            }

            count += total
            currentPage.add(tool)
        }
        if (currentPage.isNotEmpty()) {
            pageResults.add(currentPage)
        }
        for (page in pageResults) {
            var r = Component.text("");
            for (tool in page) {
                r = r.append(Component.text("> ${tool.name}\n", Style.style(TextDecoration.BOLD)))
                    .append(Component.text(tool.description + "\n\n"))
            }
            bookMeta.addPages(r)
        }


        book.itemMeta = bookMeta

        val recipe = ShapedRecipe(recipeKey, book)

        recipe.shape("   ", " W ", "   ")

        recipe.setIngredient('W', Material.WHEAT_SEEDS)

        init(recipeKey, recipe, arrayOf(Material.WHEAT_SEEDS))
    }
}