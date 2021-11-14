package io.github.clayclaw.islandcore

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.reactant.reactant.core.ReactantPlugin
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.stat.type.DoubleStat
import net.Indyuce.mmoitems.stat.type.StringStat
import net.brian.islandcore.data.gson.PostProcessable.PostProcessingEnabler
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Executor

@ReactantPlugin([
    "io.github.clayclaw.islandcore",
    "net.brian.islandcore"
])
class IslandCore : JavaPlugin() {

    override fun onEnable() {
        instance = this
        gson = GsonBuilder().registerTypeAdapterFactory(PostProcessingEnabler(this)).create()
    }

    override fun onLoad() {
        MMOItems.plugin.stats.register(
            StringStat(
                "SEED",
                Material.WHEAT_SEEDS,
                "種子類型",
                arrayOf("種下的種子類型"),
                arrayOf("all")
            )
        )
        MMOItems.plugin.stats.register(DoubleStat("FODDER", Material.WHEAT, "飼料強度", arrayOf("飼料的恢復得飽實度")))
    }

    companion object {
        @JvmStatic
        val log: Logger = LogManager.getLogger("IslandCore")
        @JvmStatic
        lateinit var instance: IslandCore
        @JvmStatic
        fun getExecutor(): Executor {
            return Bukkit.getScheduler().getMainThreadExecutor(instance)
        }
        @JvmStatic
        lateinit var gson: Gson
    }

}
