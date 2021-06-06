package io.github.plugin.Shield

import io.github.plugin.Shield.ShieldListener.ShieldListener
import org.bukkit.plugin.java.JavaPlugin

class Shield: JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(ShieldListener(this), this)
    }
}