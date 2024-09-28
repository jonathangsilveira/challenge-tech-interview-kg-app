package edu.jgsilveira.challenge.kg.presentation.mapper

import edu.jgsilveira.challenge.kg.domain.model.ActiveEvent
import edu.jgsilveira.challenge.kg.domain.model.SportEvent
import edu.jgsilveira.challenge.kg.presentation.model.ActiveSportEventViewData
import edu.jgsilveira.challenge.kg.presentation.model.SportEventViewData

internal fun List<SportEvent>.toViewData(
    favoriteActiveSportEventIds: List<String>,
    filteredSportEventId: String?
): List<SportEventViewData> {
    return map { sport ->
        SportEventViewData(
            id = sport.id,
            title = sport.name,
            isFilterChecked = sport.id == filteredSportEventId,
            activeEvents = sport.activeEvents.map { event ->
                event.toViewData(
                    favoriteActiveSportEventIds
                )
            }
        )
    }
}

private fun ActiveEvent.toViewData(
    favoriteActiveSportEventIds: List<String>
) = ActiveSportEventViewData(
    id = id,
    homeCompetitor = homeCompetitor,
    awayCompetitor = awayCompetitor,
    startsAt = startsAt,
    isFavorite = favoriteActiveSportEventIds.contains(id)
)