package edu.jgsilveira.challenge.kg.domain.repository

import edu.jgsilveira.challenge.kg.domain.model.SportEvent

interface SportEventsRepository {
    suspend fun getActiveEvents(): List<SportEvent>
    suspend fun getActiveEventsBySport(sportId: String): List<SportEvent>
}