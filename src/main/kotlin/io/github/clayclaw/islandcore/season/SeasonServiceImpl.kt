package io.github.clayclaw.islandcore.season

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.extra.net.RetrofitJsonAPI
import dev.reactant.reactant.service.spec.server.SchedulerService
import io.github.clayclaw.islandcore.season.request.SeasonAPI
import io.reactivex.rxjava3.schedulers.Schedulers

@Component
class SeasonServiceImpl(
    private val seasonAPI: RetrofitJsonAPI<SeasonAPI>,
    private val schedulerService: SchedulerService
): SeasonService, LifeCycleHook {

    override fun onEnable() {
        seasonAPI.service.getSeason()
            .subscribeOn(Schedulers.io())
            .observeOn(schedulerService.mainThreadScheduler)
            .subscribe {

            }
    }

    override fun getCurrentSeason(): SeasonType {

    }

}