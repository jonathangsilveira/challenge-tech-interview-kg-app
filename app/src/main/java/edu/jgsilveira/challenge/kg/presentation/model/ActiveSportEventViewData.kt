package edu.jgsilveira.challenge.kg.presentation.model

internal data class ActiveSportEventViewData(
    val id: String,
    val homeCompetitor: String,
    val awayCompetitor: String,
    val startsAt: Long,
    val isFavorite: Boolean = false
)
