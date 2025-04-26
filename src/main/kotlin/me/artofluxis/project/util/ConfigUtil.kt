package me.artofluxis.project.util

import me.artofluxis.project.plugin
import org.bukkit.Material

val readMaterialLists = hashMapOf<String, List<Material>>()
val readMaterialMaps = hashMapOf<String, HashMap<Material, Pair<Material, Int>>>()
val readBooleans = hashMapOf<String, Boolean>()

fun loadMaterialList(id: String): List<Material> {
    if (id in readMaterialLists) return readMaterialLists[id]!!
    else return plugin.config.getStringList(id).map { Material.valueOf(it) }.also { readMaterialLists[id] = it }
}

fun loadBoolean(id: String): Boolean {
    if (id in readBooleans) return readBooleans[id]!!
    else return plugin.config.getBoolean(id)
}

fun loadMaterialMap(id: String): HashMap<Material, Pair<Material, Int>> {
    if (id in readMaterialMaps) return readMaterialMaps[id]!!
    else return HashMap(plugin.config.getStringList(id).associate { original ->
        original.split(" to ").let {
            val data = it[1].split(":")
            val amount = if (data.size != 1) data[1].toInt() else 1

            Material.valueOf(it[0]) to (Material.valueOf(data[0]) to amount)
        }
    }).also { readMaterialMaps[id] = it }
}