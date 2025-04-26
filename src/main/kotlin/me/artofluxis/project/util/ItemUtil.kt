package me.artofluxis.project.util

import me.artofluxis.project.plugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun item(material: Material, amount: Int = 1) = ItemStack(material, amount)

fun item(material: Material, amount: Int = 1, editMeta: ItemMeta.() -> Unit): ItemStack =
    ItemStack(material, amount).apply {
        itemMeta = itemMeta?.apply(editMeta)
    }

fun item(item: ItemStack, editMeta: ItemMeta.() -> Unit): ItemStack =
    item.clone().apply {
        itemMeta = itemMeta?.apply(editMeta)
    }

@ExperimentalContracts
fun ItemStack?.isNullOrAir(): Boolean {
    contract {
        returns(false) implies (this@isNullOrAir is ItemStack)
    }
    return this == null || type.isAir
}

fun key(key: String): NamespacedKey = NamespacedKey(plugin, key)
