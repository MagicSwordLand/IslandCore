package io.github.clayclaw.islandcore.updater

interface AliveIslandUpdater {

    val cycleTime: Long
    fun onUpdate()

}