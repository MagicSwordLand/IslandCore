package io.github.clayclaw.islandcore

import dev.reactant.reactant.core.ReactantPlugin
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.plugin.java.JavaPlugin

@ReactantPlugin([
    "io.github.clayclaw.islandcore",
    "net.brian.islandcore"
])
class IslandCore : JavaPlugin() {

    override fun onEnable() {
        instance = this
    }

    companion object {
        @JvmStatic
        val log: Logger = LogManager.getLogger("IslandCore")
        @JvmStatic
        lateinit var instance: IslandCore
    }

}
