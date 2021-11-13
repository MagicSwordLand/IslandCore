package io.github.clayclaw.islandcore.updater

import world.bentobox.bentobox.database.objects.Island

interface AliveIslandUpdater {

    val cycleTime: Long
    fun onUpdate(island: Island)

}