package me.artofluxis.project.util

import me.artofluxis.project.plugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import kotlin.time.Duration

/**
 * Function to run a scheduler loop.
 * @param period The interval at which the code is executed
 * @param delay Delay before starting in ticks
 * @param action Code to be executed in each iteration of the loop
 */
fun runTaskTimer(period: Duration, delay: Int = 0, action: (BukkitTask) -> Unit) {
    Bukkit.getScheduler().runTaskTimer(plugin, { task -> action(task) }, delay.toLong(), period.toTicks())
}

/**
 * Function to run an async scheduler loop.
 * @param period The interval at which the code is executed
 * @param delay Delay before starting in ticks
 * @param action Code to be executed in each iteration of the loop
 */
fun runTaskTimerAsync(period: Duration, delay: Int = 0, action: (BukkitTask) -> Unit) {
    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, { task -> action(task) }, delay.toLong(), period.toTicks())
}

fun Duration.toTicks(): Long = this.inWholeMilliseconds / 50

/**
 * Function to run a delayed task after the specified number of ticks.
 * @param delay Delay before execution in ticks
 * @param action Code to be executed after the delay
 */
fun runTaskLater(delay: Duration, action: BukkitTask.() -> Unit) {
    Bukkit.getScheduler().runTaskLater(plugin, { task -> task.action() }, delay.toTicks())
}

/**
 * Function to asynchronously run a delayed task after the specified number of ticks.
 * @param delay Delay before execution in ticks
 * @param action Code to be executed after the delay
 */
fun runTaskLaterAsync(delay: Duration, action: BukkitTask.() -> Unit) {
    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, { task -> task.action() }, delay.toTicks())
}

/**
 * Function to run a task asynchronously to avoid blocking the server's main thread.
 * @param action Code to be executed asynchronously
 */
fun runTaskAsync(action: BukkitTask.() -> Unit) {
    Bukkit.getScheduler().runTaskAsynchronously(plugin, action)
}