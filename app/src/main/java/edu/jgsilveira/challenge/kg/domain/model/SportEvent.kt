package edu.jgsilveira.challenge.kg.domain.model

data class SportEvent(
    val id: String,
    val name: String,
    val activeEvents: List<ActiveEvent>
)
