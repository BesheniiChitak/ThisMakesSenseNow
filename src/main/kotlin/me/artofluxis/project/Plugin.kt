@file:Suppress("UnstableApiUsage", "unused")
package me.artofluxis.project

import me.artofluxis.project.listeners.GlobalListener
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

lateinit var plugin: Plugin

class Plugin : JavaPlugin() {
    override fun onEnable() {
        plugin = this

        Bukkit.getPluginManager().registerEvents(GlobalListener, plugin)

        saveDefaultConfig()

        val version = plugin.config.getInt("config_version", -1)
        if (version != 1) {
            File(plugin.dataFolder, "config.yml").delete()
            saveDefaultConfig()
            Bukkit.getConsoleSender().sendMessage("[This Makes Sense Now] The plugin's config was replaced with a default one due to an invalid config version")
            reloadConfig()
        }
    }

    override fun onDisable() {}
}