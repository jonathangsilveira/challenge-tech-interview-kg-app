package edu.jgsilveira.challenge.kg.domain.usecase

import edu.jgsilveira.challenge.kg.domain.repository.FavoriteSportsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFavoriteSportEventIdsUseCase(
    private val favoriteSportEventsRepository: FavoriteSportsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): List<String> {
        return withContext(ioDispatcher) {
            favoriteSportEventsRepository.getFavoriteIds()
        }
    }
}