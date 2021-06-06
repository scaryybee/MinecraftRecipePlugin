package io.github.plugin.Shield.ShieldListener

import io.github.plugin.Shield.Shield
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta

class ShieldListener(private val plugin: Shield): Listener {
    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        if (entity.type == EntityType.PLAYER) {
            val victim = entity as Player
            val mainhanditem = victim.inventory.itemInMainHand
            val offhanditem = victim.inventory.itemInOffHand
            if (mainhanditem.type == Material.SHIELD) {
                val shield = mainhanditem
                val shield_meta = shield.itemMeta as Damageable
                val damaged = shield_meta.damage
                shield_meta.damage = damaged + event.damage.toInt()
                shield.itemMeta = shield_meta as ItemMeta
            } else if (offhanditem.type == Material.SHIELD) {
                val shield = offhanditem
                val shield_meta = shield.itemMeta as Damageable
                val damaged = shield_meta.damage
                shield_meta.damage = damaged + event.damage.toInt()
                shield.itemMeta = shield_meta as ItemMeta
            }
        }
    }
}