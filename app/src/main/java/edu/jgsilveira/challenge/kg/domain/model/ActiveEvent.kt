package edu.jgsilveira.challenge.kg.domain.model

data class ActiveEvent(
    val id: String,
    val homeCompetitor: String,
    val awayCompetitor: String,
    val startsAt: Long,
    val sportId: String
)
