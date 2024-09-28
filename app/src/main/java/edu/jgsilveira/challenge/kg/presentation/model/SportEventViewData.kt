package edu.jgsilveira.challenge.kg.presentation.model

internal data class SportEventViewData(
    val id: String,
    val title: String,
    val activeEvents: List<ActiveSportEventViewData>,
    val isFilterChecked: Boolean = false
)
