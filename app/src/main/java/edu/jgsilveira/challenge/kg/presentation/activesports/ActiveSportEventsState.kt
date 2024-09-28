package edu.jgsilveira.challenge.kg.presentation.activesports

import edu.jgsilveira.challenge.kg.presentation.model.SportEventViewData

internal data class ActiveSportEventsState(
    val isLoading: Boolean = false,
    val sportEventId: String? = null,
    val error: ActiveSportEventsUIError? = null,
    val results: List<SportEventViewData> = emptyList()
)
