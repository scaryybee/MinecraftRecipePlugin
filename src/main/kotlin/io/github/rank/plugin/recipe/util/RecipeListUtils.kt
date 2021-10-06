package io.github.rank.plugin.recipe.util

import io.github.rank.plugin.recipe.itemStack
import io.github.rank.plugin.recipe.number
import io.github.rank.plugin.recipe.recipeConfig
import io.github.rank.plugin.recipe.recipeResult
import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui.gui
import net.projecttl.inventory.gui.utils.InventoryType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

private const val itemsInPage = 54

private infix fun Int.to(to: Int) = this..to

private fun Player.openRecipe(plugin: Plugin, recipeNumber: Int) {
    this.gui(plugin, InventoryType.CHEST_27, Component.text("Recipe")) {
        (0 until 27).forEach { slotNumber ->
            slot(slotNumber, ItemStack(Material.BARRIER).apply {
                editMeta {
                    it.displayName(Component.text(" "))
                }
            })
        }

        (0 until 9).forEach {
            slot(it, recipeConfig.itemStack("$recipeNumber.recipe.$it"))
        }

        slot(recipeResult, recipeConfig.itemStack("$recipeNumber.result"))

        slot(26, ItemStack(Material.ARROW).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}뒤로 가기"))
            }
        }) {
            this@openRecipe.openRecipeList(plugin, (recipeNumber - 1) / itemsInPage + 1)
        }

        // TODO: 2021-10-07 Add Delete Recipe Button.
    }
}

fun Player.openRecipeList(plugin: Plugin, page: Int) {
    this.gui(plugin, InventoryType.CHEST_54, Component.text("Recipe-List")) {
        val start = (page - 1) * itemsInPage
        for (i in start to number - start) {
            slot(i, recipeConfig.itemStack("$i.result")) {
                this@openRecipeList.openRecipe(plugin, i)
            }
        }

        (45 until 54).forEach { slotNumber ->
            slot(slotNumber, ItemStack(Material.BARRIER).apply {
                editMeta {
                    it.displayName(Component.text(" "))
                }
            })
        }

        slot(45, ItemStack(Material.ARROW).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}뒤 페이지"))
            }
        })

        slot(49, ItemStack(Material.BOOK).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}현재 페이지 $page"))
            }
        })

        slot(53, ItemStack(Material.ARROW).apply {
            editMeta {
                it.displayName(Component.text("${ChatColor.GREEN}앞 페이지"))
            }
        })

        // TODO: 2021-10-07 Make Recipe List Page Working
    }
}