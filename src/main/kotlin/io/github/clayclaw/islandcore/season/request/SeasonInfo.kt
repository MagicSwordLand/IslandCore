package io.github.clayclaw.islandcore.season.request

import io.github.clayclaw.islandcore.season.SeasonType

data class SeasonInfo(
    var type: SeasonType,
    var time: Int
)
