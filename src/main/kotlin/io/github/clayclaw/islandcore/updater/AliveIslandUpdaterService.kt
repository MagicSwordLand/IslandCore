package io.github.clayclaw.islandcore.updater

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.components.Components
import dev.reactant.reactant.service.spec.server.EventService
import dev.reactant.reactant.service.spec.server.SchedulerService
import io.reactivex.rxjava3.disposables.Disposable
import net.brian.islandcore.data.events.IslandDataLoadCompleteEvent
import net.brian.islandcore.data.events.IslandDataPrepareSaveEvent

@Component
class AliveIslandUpdaterService(
    private val updaters: Components<AliveIslandUpdater>,
    private val eventService: EventService,
    private val schedulerService: SchedulerService
): LifeCycleHook {

    private val updaterDisposables: HashMap<String, MutableList<Disposable>> = hashMapOf()

    override fun onEnable() {
        eventService {
            IslandDataLoadCompleteEvent::class.observable()
                .subscribe { e ->
                    updaterDisposables[e.island.uniqueId] = updaters
                        .map {
                            schedulerService.interval(it.cycleTime).subscribe { _ ->
                                it.onUpdate()
                            }
                        }
                        .toMutableList()
                }

            IslandDataPrepareSaveEvent::class.observable()
                .subscribe { e ->
                    updaterDisposables[e.island.uniqueId]?.forEach {
                        it.dispose()
                    }
                    updaterDisposables.remove(e.island.uniqueId)
                }
        }
    }

}