package me.artofluxis.project.listeners

import me.artofluxis.project.util.loadBoolean
import me.artofluxis.project.util.loadMaterialList
import me.artofluxis.project.util.loadMaterialMap
import me.artofluxis.project.util.runTaskLater
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Levelled
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import kotlin.time.Duration.Companion.seconds

private fun onBlockBreakInAnyWay(block: Block) {
    if (loadBoolean("liquids_from_cauldrons")) {
        val type = block.type
        val shouldSpill = when (type) {
            Material.WATER_CAULDRON -> (block.blockData as Levelled).level == 3
            Material.LAVA_CAULDRON -> true
            else -> false
        }
        if (shouldSpill) {
            runTaskLater(0.05.seconds) {
                when (type) {
                    Material.WATER_CAULDRON -> block.type = Material.WATER
                    Material.LAVA_CAULDRON -> block.type = Material.LAVA
                    else -> {}
                }
            }
        }
    }
}

@Suppress("unused")
object GlobalListener : Listener {
    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is Item) {
            val itemStack = entity.itemStack
            val itemStackType = itemStack.type
            if (event.cause in hashSetOf(
                    EntityDamageEvent.DamageCause.FIRE,
                    EntityDamageEvent.DamageCause.LAVA
            )) {
                if (itemStackType in loadMaterialList("unflammable_items")) {
                    event.isCancelled = true
                }
                else {
                    val resultsMap = loadMaterialMap("flammable_items")
                    if (itemStackType in resultsMap) {
                        event.isCancelled = true
                        val result = resultsMap[itemStackType]!!
                        var amount = result.second * itemStack.amount
                        val meta = itemStack.itemMeta
                        if (meta is Damageable && itemStackType.maxDurability > 0) {
                            amount = (amount * (itemStackType.maxDurability - meta.damage).toDouble() / itemStackType.maxDurability).toInt()
                        }

                        if (amount > 0) entity.itemStack = ItemStack(result.first, amount)
                        else event.isCancelled = false
                    }
                }
            } else if (event.cause in hashSetOf(
                    EntityDamageEvent.DamageCause.ENTITY_EXPLOSION,
                    EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
            )) {
                if (itemStackType in loadMaterialList("unexplodable_items")) {
                    event.isCancelled = true
                }
            }

        }
    }

    @EventHandler
    fun onBlockBreak1(event: BlockBreakEvent) { onBlockBreakInAnyWay(event.block) }
    @EventHandler
    fun onBlockBreak2(event: BlockExplodeEvent) {
        val iterator = event.blockList().iterator()
        iterator.forEach { onBlockBreakInAnyWay(it) }
    }
    @EventHandler
    fun onBlockBreak3(event: EntityExplodeEvent) {
        val iterator = event.blockList().iterator()
        iterator.forEach { onBlockBreakInAnyWay(it) }
    }
}