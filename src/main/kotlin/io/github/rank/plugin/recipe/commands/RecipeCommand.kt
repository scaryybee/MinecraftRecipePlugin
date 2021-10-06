package io.github.rank.plugin.recipe.commands

import io.github.rank.plugin.recipe.AddRecipePlugin
import io.github.rank.plugin.recipe.loadRecipe
import io.github.rank.plugin.recipe.reloadRecipe
import io.github.rank.plugin.recipe.unloadRecipe
import io.github.rank.plugin.recipe.util.openAddRecipeGUI
import io.github.rank.plugin.recipe.util.openRecipeList
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RecipeCommand(private val plugin: AddRecipePlugin) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        when (command.name) {
            "recipe" -> {
                when (args[0]) {
                    "add" -> sender.openAddRecipeGUI(plugin)

                    "list" -> sender.openRecipeList(plugin, 1)

                    "reload" -> plugin.reloadRecipe()

                    "unload" -> plugin.unloadRecipe()

                    "load" -> plugin.loadRecipe()
                }
            }
        }
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        val commandList = mutableListOf<String>()

        if (command.name == "recipe") {
            when (args.size) {
                1 -> {
                    commandList.add("add")
                    commandList.add("list")
                    commandList.add("reload")
                    commandList.add("unload")
                    commandList.add("load")
                }
            }
        }

        return commandList
    }
}