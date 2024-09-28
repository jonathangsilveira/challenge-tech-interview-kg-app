package edu.jgsilveira.challenge.kg.domain.usecase

import edu.jgsilveira.challenge.kg.domain.model.ActiveEvent
import edu.jgsilveira.challenge.kg.domain.model.SportEvent

internal object GetActiveSportEventsStub {
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