package io.github.clayclaw.islandcore.season.request

import dev.reactant.reactant.extra.net.BaseUrl
import io.reactivex.rxjava3.core.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

@BaseUrl("")
interface SeasonAPI {

    @GET("seasons/")
    fun getSeason(): Maybe<SeasonInfo>

}