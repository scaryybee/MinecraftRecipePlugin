package io.github.rank.plugin.recipe

import io.github.rank.plugin.recipe.commands.RecipeCommand
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

val recipeList = listOf(2, 3, 4, 11, 12, 13, 20, 21, 22) // Basic 3 X 3

const val recipeResult = 15

val recipeListFile = File("plugins/AddRecipe/config.yml")
val recipeConfig = YamlConfiguration.loadConfiguration(recipeListFile)

var number: Int = 0

fun YamlConfiguration.itemStack(path: String) =
    if (this.isSet(path)) this.getItemStack(path)!! else ItemStack(Material.AIR)

fun Plugin.loadRecipe() {
    for (i in 0 until number) {
        when (recipeConfig.getInt("$i.type")) {
            1 -> {
                val recipe = ShapedRecipe(NamespacedKey(this, "add-recipe"),
                    recipeConfig.itemStack("$i.result")).shape("a")
                    .setIngredient('a', recipeConfig.itemStack("$i.recipe.0"))

                server.addRecipe(recipe)
            }

            2 -> {
                val recipe = ShapedRecipe(NamespacedKey(this, "add-recipe"),
                    recipeConfig.itemStack("$i.result")).shape("ab", "cd")
                    .setIngredient('a', recipeConfig.itemStack("$i.recipe.0"))
                    .setIngredient('b', recipeConfig.itemStack("$i.recipe.1"))
                    .setIngredient('c', recipeConfig.itemStack("$i.recipe.2"))
                    .setIngredient('d', recipeConfig.itemStack("$i.recipe.3"))

                server.addRecipe(recipe)
            }

            3 -> {
                val recipe = ShapedRecipe(NamespacedKey(this, "add-recipe"),
                    recipeConfig.itemStack("$i.result")).shape("abc", "def", "ghi")
                    .setIngredient('a', recipeConfig.itemStack("$i.recipe.0"))
                    .setIngredient('b', recipeConfig.itemStack("$i.recipe.1"))
                    .setIngredient('c', recipeConfig.itemStack("$i.recipe.2"))
                    .setIngredient('d', recipeConfig.itemStack("$i.recipe.3"))
                    .setIngredient('e', recipeConfig.itemStack("$i.recipe.4"))
                    .setIngredient('f', recipeConfig.itemStack("$i.recipe.5"))
                    .setIngredient('g', recipeConfig.itemStack("$i.recipe.6"))
                    .setIngredient('h', recipeConfig.itemStack("$i.recipe.7"))
                    .setIngredient('i', recipeConfig.itemStack("$i.recipe.8"))

                server.addRecipe(recipe)
            }

            4 -> {
                val recipe = ShapelessRecipe(NamespacedKey(this, "add-recipe"),
                    recipeConfig.itemStack("$i.result"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.0"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.1"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.2"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.3"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.4"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.5"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.6"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.7"))
                    .addIngredient(recipeConfig.itemStack("$i.recipe.8"))

                server.addRecipe(recipe)
            }
        }
    }

    logger.info("${ChatColor.GREEN}Recipe Loaded Successful!")
}

fun Plugin.unloadRecipe() {
    (0 until number).forEach {
        this.server.removeRecipe(NamespacedKey(this, "$it"))
    }

    logger.info("${ChatColor.RED}Recipe Unloaded Successful!")
}

fun Plugin.reloadRecipe() {
    unloadRecipe()
    loadRecipe()

    logger.info("${ChatColor.GREEN}Recipe Reloaded Successful!")
}

class AddRecipePlugin : JavaPlugin() {
    override fun onEnable() {
        try {
            if (!recipeListFile.exists()) {
                recipeConfig.save(recipeListFile)
            }
            recipeConfig.load(recipeListFile)
        } catch (localException: Exception) {
            localException.printStackTrace()
        }
        if (recipeConfig.isSet("number")) number = recipeConfig.getInt("number")
        else recipeConfig.set("number", 0)

        server.getPluginCommand("recipe")?.apply {
            setExecutor(RecipeCommand(this@AddRecipePlugin))
            tabCompleter = RecipeCommand(this@AddRecipePlugin)
        }

        // loadRecipe()
    }

    override fun onDisable() {
        recipeConfig.set("number", number)
        recipeConfig.save(recipeListFile)

        // unloadRecipe()
    }
}