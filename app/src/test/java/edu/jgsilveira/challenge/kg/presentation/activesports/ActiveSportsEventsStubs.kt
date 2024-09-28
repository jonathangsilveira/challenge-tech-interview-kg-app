package edu.jgsilveira.challenge.kg.presentation.activesports

import edu.jgsilveira.challenge.kg.domain.model.ActiveEvent
import edu.jgsilveira.challenge.kg.domain.model.SportEvent
import edu.jgsilveira.challenge.kg.presentation.model.ActiveSportEventViewData
import edu.jgsilveira.challenge.kg.presentation.model.SportEventViewData

internal object ActiveSportsEventsStubs {
    val events = listOf(
        SportEventViewData(
            id = "foot",
            title = "Brasileirão 2024",
            isFilterChecked = false,
            activeEvents = listOf(
                ActiveSportEventViewData(
                    id = "459370",
                    startsAt = 0L,
                    homeCompetitor = "Vasco",
                    awayCompetitor = "Fluminense",
                    isFavorite = false
                )
            )
        )
    )

    val activeSportEvents = listOf(
        SportEvent(
            id = "foot",
            name = "Brasileirão 2024",
            activeEvents = listOf(
                ActiveEvent(
                    id = "459370",
                    homeCompetitor = "Vasco",
                    awayCompetitor = "Fluminense",
                    sportId = "foot",
                    startsAt = 0L
                )
            )
        )
    )
}