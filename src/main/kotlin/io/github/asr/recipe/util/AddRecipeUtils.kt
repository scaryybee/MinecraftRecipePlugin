package io.github.asr.recipe.util

import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.Plugin

fun Player.openAddRecipeGUI(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        slot(10, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}1 X 1"))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVer1(plugin)
        }

        slot(12, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}2 X 2"))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVer2(plugin)
        }

        slot(14, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}3 X 3"))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVer3(plugin)
        }

        slot(16, ItemStack(Material.CRAFTING_TABLE).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}SHAPELESS"))
            }
        }) {
            this@openAddRecipeGUI.openAddRecipeGUIVerShapeless(plugin)
        }
    }
}

private fun Inventory.item(slot: Int) = if (getItem(slot) == null) ItemStack(Material.AIR) else getItem(slot)!!

private fun Player.openAddRecipeGUIVer1(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (slotNumber != 12 && slotNumber != 15)
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}Add Recipe"))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.item(15).type == Material.AIR) {
                this@openAddRecipeGUIVer1.sendMessage(Component.text(
                    "${ChatColor.RED}You can't add recipe that result is AIR!"
                ))
            }

            if (inv.item(12).type == Material.AIR) {
                this@openAddRecipeGUIVer1.sendMessage(Component.text("${ChatColor.RED}Recipe must not be added by only AIR!"))
                return@slot
            }

            val shapedRecipe = ShapedRecipe(NamespacedKey(plugin, "$number"),
                inv.item(15)).shape("a").setIngredient('a', inv.item(12))

            plugin.server.addRecipe(shapedRecipe)

            val type = 1
            recipeConfig.set("$number.type", type)
            recipeConfig.set("$number.recipe.0", inv.item(12))
            recipeConfig.set("$number.result", inv.item(recipeResult))

            number++

            this@openAddRecipeGUIVer1.closeInventory()

            recipeConfig.save(recipeListFile)
        }
    }
}

private fun Player.openAddRecipeGUIVer2(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (!recipeList.contains(slotNumber) && recipeResult != slotNumber ||
                (slotNumber == 4 || slotNumber == 13 || slotNumber > 15))
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}Add Recipe"))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.item(15).type == Material.AIR) {
                this@openAddRecipeGUIVer2.sendMessage(Component.text(
                    "${ChatColor.RED}You can't add recipe that result is AIR!"
                ))
            }

            if (inv.item(2).type == Material.AIR && inv.item(3).type == Material.AIR
                && inv.item(11).type == Material.AIR && inv.item(12).type == Material.AIR) {
                this@openAddRecipeGUIVer2.sendMessage(Component.text("${ChatColor.RED}Recipe must not be added by only AIR!"))
                return@slot
            }

            val shapedRecipe = ShapedRecipe(NamespacedKey(plugin, "$number"),
                inv.item(15)).shape("ab", "cd").setIngredient('a', inv.item(2))
                .setIngredient('b', inv.item(3)).setIngredient('c', inv.item(11))
                .setIngredient('d', inv.item(12))

            plugin.server.addRecipe(shapedRecipe)

            val type = 2
            recipeConfig.set("$number.type", type)
            recipeConfig.set("$number.recipe.0", inv.item(recipeList[0]))
            recipeConfig.set("$number.recipe.1", inv.item(recipeList[1]))
            recipeConfig.set("$number.recipe.2", inv.item(recipeList[3]))
            recipeConfig.set("$number.recipe.3", inv.item(recipeList[4]))
            recipeConfig.set("$number.result", inv.item(recipeResult))

            number++

            recipeConfig.save(recipeListFile)

            this@openAddRecipeGUIVer2.closeInventory()
        }
    }
}

private fun Player.openAddRecipeGUIVer3(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (!recipeList.contains(slotNumber) && recipeResult != slotNumber)
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}Add Recipe"))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.item(15).type == Material.AIR) {
                this@openAddRecipeGUIVer3.sendMessage(Component.text(
                    "${ChatColor.RED}You can't add recipe that result is AIR!"
                ))
            }

            recipeList.forEach {
                if (inv.item(it).type != Material.AIR) {
                    val shapedRecipe = ShapedRecipe(NamespacedKey(plugin, "$number"),
                        inv.item(15)).shape("abc", "def", "ghi").setIngredient('a', inv.item(2))
                        .setIngredient('b', inv.item(3)).setIngredient('c', inv.item(4))
                        .setIngredient('d', inv.item(11)).setIngredient('e', inv.item(12))
                        .setIngredient('f', inv.item(13)).setIngredient('g', inv.item(20))
                        .setIngredient('h', inv.item(21)).setIngredient('i', inv.item(22))

                    plugin.server.addRecipe(shapedRecipe)

                    val type = 3
                    recipeConfig.set("$number.type", type)
                    for (i in 0 until type * type) {
                        recipeConfig.set("$number.recipe.$i", inv.item(recipeList[i]))
                    }
                    recipeConfig.set("$number.result", inv.item(recipeResult))

                    number++

                    recipeConfig.save(recipeListFile)

                    this@openAddRecipeGUIVer3.closeInventory()

                    return@slot
                }
            }

            this@openAddRecipeGUIVer3.sendMessage(Component.text("${ChatColor.RED}Recipe must not be added by only AIR!"))
        }
    }
}

private fun Player.openAddRecipeGUIVerShapeless(plugin: Plugin) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Add-Recipe")) {
        (0 until 27).forEach { slotNumber ->
            if (!recipeList.contains(slotNumber) && recipeResult != slotNumber)
                slot(slotNumber, ItemStack(Material.BARRIER).apply {
                    editMeta {
                        it.displayName(Component.text(" "))
                    }
                }) else {
                slot(slotNumber, ItemStack(Material.AIR), false)
            }
        }

        slot(26, ItemStack(Material.EMERALD_BLOCK).apply {
            editMeta {
                displayName(Component.text("${ChatColor.GREEN}Add Recipe"))
            }
        }) {
            val inv = clickedInventory!!

            if (inv.item(15).type == Material.AIR) {
                this@openAddRecipeGUIVerShapeless.sendMessage(Component.text(
                    "${ChatColor.RED}You can't add recipe that result is AIR!"
                ))
            }

            recipeList.forEach {
                if (inv.item(it).type != Material.AIR) {
                    val shapelessRecipe = ShapelessRecipe(NamespacedKey(plugin, "$number"), inv.item(15))
                        .addIngredient(inv.item(2)).addIngredient(inv.item(3)).addIngredient(inv.item(4))
                        .addIngredient(inv.item(11)).addIngredient(inv.item(12)).addIngredient(inv.item(13))
                        .addIngredient(inv.item(20)).addIngredient(inv.item(21)).addIngredient(inv.item(22))

                    plugin.server.addRecipe(shapelessRecipe)

                    val type = 4
                    recipeConfig.set("$number.type", type)
                    for (i in 0 until 9) {
                        recipeConfig.set("$number.recipe.$i", inv.item(recipeList[i]))
                    }
                    recipeConfig.set("$number.result", inv.item(recipeResult))
                    number++

                    recipeConfig.save(recipeListFile)

                    this@openAddRecipeGUIVerShapeless.closeInventory()

                    return@slot
                }
            }

            this@openAddRecipeGUIVerShapeless.sendMessage(
                Component.text("${ChatColor.RED}Recipe must not be added by only AIR!")
            )
        }
    }
}
