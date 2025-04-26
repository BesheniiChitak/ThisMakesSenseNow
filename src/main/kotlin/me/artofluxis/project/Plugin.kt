@file:Suppress("UnstableApiUsage", "unused")
package me.artofluxis.project

import me.artofluxis.project.listeners.GlobalListener
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: Plugin

class Plugin : JavaPlugin() {
    override fun onEnable() {
        plugin = this

        Bukkit.getPluginManager().registerEvents(GlobalListener, plugin)

        saveDefaultConfig()
    }

    override fun onDisable() {}
}