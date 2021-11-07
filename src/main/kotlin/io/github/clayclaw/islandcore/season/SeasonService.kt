package io.github.clayclaw.islandcore.season

interface SeasonService {

    /**
     * @return the current server season
     */
    fun getCurrentSeason(): SeasonType

}