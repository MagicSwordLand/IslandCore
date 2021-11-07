package io.github.clayclaw.islandcore.block

import org.bukkit.block.Block
import world.bentobox.bentobox.database.objects.Island

fun Island.blockAt(x: Double, y: Double, z: Double): Block {
    return center.add(x, y, z).block
}

fun Island.blockAt(x: Int, y: Int, z: Int): Block {
    return blockAt(x.toDouble(), y.toDouble(), z.toDouble())
}