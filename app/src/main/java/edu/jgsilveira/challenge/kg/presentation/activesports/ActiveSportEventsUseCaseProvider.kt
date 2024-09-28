package edu.jgsilveira.challenge.kg.presentation.activesports

import edu.jgsilveira.challenge.kg.domain.usecase.HandleFavoriteActiveSportEventChangesUseCase
import edu.jgsilveira.challenge.kg.domain.usecase.GetActiveSportEventsUseCase
import edu.jgsilveira.challenge.kg.domain.usecase.GetFavoriteSportEventIdsUseCase

internal class ActiveSportEventsUseCaseProvider(
    val getActiveSportEvents: GetActiveSportEventsUseCase,
    val handleFavoriteActiveSportEventChanges: HandleFavoriteActiveSportEventChangesUseCase,
    val getFavoriteSportEventIds: GetFavoriteSportEventIdsUseCase
)
