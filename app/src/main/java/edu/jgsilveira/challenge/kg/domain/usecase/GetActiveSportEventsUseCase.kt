package edu.jgsilveira.challenge.kg.domain.usecase

import edu.jgsilveira.challenge.kg.domain.exception.ActiveSportEventsNotFoundException
import edu.jgsilveira.challenge.kg.domain.model.SportEvent
import edu.jgsilveira.challenge.kg.domain.repository.SportEventsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetActiveSportEventsUseCase(
    private val sportEventsRepository: SportEventsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        sportId: String?
    ): List<SportEvent> {
        return withContext(ioDispatcher) {
            val sportEvents = if (sportId.isNullOrEmpty()) {
                sportEventsRepository.getActiveEvents()
            } else {
                sportEventsRepository.getActiveEventsBySport(sportId)
            }
            sportEvents.takeIf { it.isNotEmpty() }
                ?: throw ActiveSportEventsNotFoundException()
        }
    }
}