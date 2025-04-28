package me.artofluxis.project.util

import me.artofluxis.project.plugin
import org.bukkit.Material

val readMaterialLists = hashMapOf<String, HashSet<Material>>()
val readMaterialMaps = hashMapOf<String, HashMap<Material, Pair<Material, Int>>>()
val readBooleans = hashMapOf<String, Boolean>()

fun loadMaterialList(id: String): HashSet<Material> {
    return if (id in readMaterialLists) readMaterialLists[id]!!
    else plugin.config.getStringList(id).map { Material.valueOf(it) }.toHashSet().also { readMaterialLists[id] = it }
}

fun loadBoolean(id: String): Boolean {
    return if (id in readBooleans) readBooleans[id]!!
    else plugin.config.getBoolean(id)
}

fun loadMaterialMap(id: String): HashMap<Material, Pair<Material, Int>> {
    return if (id in readMaterialMaps) readMaterialMaps[id]!!
    else HashMap(plugin.config.getStringList(id).associate { original ->
        original.split(" to ").let {
            val data = it[1].split(":")
            val amount = if (data.size != 1) data[1].toInt() else 1

            Material.valueOf(it[0]) to (Material.valueOf(data[0]) to amount)
        }
    }).also { readMaterialMaps[id] = it }
}