package edu.jgsilveira.challenge.kg.domain.di

import edu.jgsilveira.challenge.kg.domain.usecase.HandleFavoriteActiveSportEventChangesUseCase
import edu.jgsilveira.challenge.kg.domain.usecase.GetActiveSportEventsUseCase
import edu.jgsilveira.challenge.kg.domain.usecase.GetFavoriteSportEventIdsUseCase
import org.koin.dsl.module

object DomainModuleProvider {

    val domainModule = module {
        factory {
            GetActiveSportEventsUseCase(
                sportEventsRepository = get()
            )
        }

        factory {
            HandleFavoriteActiveSportEventChangesUseCase(
                favoriteSportEventsRepository = get()
            )
        }

        factory {
            GetFavoriteSportEventIdsUseCase(
                favoriteSportEventsRepository = get()
            )
        }
    }
}