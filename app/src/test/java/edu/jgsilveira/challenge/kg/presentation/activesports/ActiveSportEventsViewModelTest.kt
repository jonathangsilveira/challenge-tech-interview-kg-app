package edu.jgsilveira.challenge.kg.presentation.activesports

import app.cash.turbine.test
import edu.jgsilveira.challenge.kg.MainDispatcherRule
import edu.jgsilveira.challenge.kg.R
import edu.jgsilveira.challenge.kg.domain.exception.ActiveSportEventsNotFoundException
import edu.jgsilveira.challenge.kg.domain.usecase.GetActiveSportEventsUseCase
import edu.jgsilveira.challenge.kg.domain.usecase.GetFavoriteSportEventIdsUseCase
import edu.jgsilveira.challenge.kg.domain.usecase.HandleFavoriteActiveSportEventChangesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

private const val SPORT_EVENT_ID = "459370"

internal class ActiveSportEventsViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getActiveSportEvents = mockk<GetActiveSportEventsUseCase>(
        relaxed = true
    )
    private val handleFavoriteSportChanges = mockk<HandleFavoriteActiveSportEventChangesUseCase>(
        relaxed = true
    )

    private val getFavoriteSportEventIds = mockk<GetFavoriteSportEventIdsUseCase>(
        relaxed = true
    )

    private val viewModel = ActiveSportEventsViewModel(
        useCaseProvider = ActiveSportEventsUseCaseProvider(
            getActiveSportEvents = getActiveSportEvents,
            handleFavoriteActiveSportEventChanges = handleFavoriteSportChanges,
            getFavoriteSportEventIds = getFavoriteSportEventIds
        )
    )

    @Test
    fun `refreshSportEvents Should change state to error When there is no data`() = runTest {
        // Given
        coEvery {
            getActiveSportEvents.invoke(null)
        } throws ActiveSportEventsNotFoundException()
        val expected = ActiveSportEventsState(
            isLoading = false,
            sportEventId = null,
            error = ActiveSportEventsUIError.NO_DATA_FOUND,
            results = emptyList()
        )

        // When
        viewModel.refreshSportEvents()

        // Then
        assertEquals(
            expected,
            viewModel.state.value
        )
        coEvery {
            getActiveSportEvents.invoke(null)
        }
    }

    @Test
    fun `refreshSportEvents Should change state to success When there is data`() {
        // Given
        val events = ActiveSportsEventsStubs.events
        coEvery {
            getActiveSportEvents.invoke(null)
        } returns ActiveSportsEventsStubs.activeSportEvents
        val expected = ActiveSportEventsState(
            isLoading = false,
            error = null,
            sportEventId = null,
            results = events
        )

        // When
        viewModel.refreshSportEvents()

        // Then
        assertEquals(
            expected,
            viewModel.state.value
        )
        coEvery {
            getActiveSportEvents.invoke(null)
        }
    }

    @Test
    fun `onActiveSportEventFavoriteChanged Should handle changes and filter When is favorite`() {
        runTest {
            // Given
            coEvery {
                handleFavoriteSportChanges.invoke(SPORT_EVENT_ID, true)
            } just runs

            // When
            viewModel.onActiveSportEventFavoriteChanged(
                activeSportEventId = SPORT_EVENT_ID,
                isFavorite = true
            )

            // Then
            coVerify {
                handleFavoriteSportChanges.invoke(SPORT_EVENT_ID, true)
            }
        }
    }

    @Test
    fun `onActiveSportEventFavoriteChanged Should send ShowErrorSnackBar When intent fails`() =
        runTest {
            // Given
            val activeSportEventId = "459370"
            val expected = ActiveSportEventsAction.ShowErrorSnackBar(
                R.string.error_add_to_favorite
            )
            coEvery {
                handleFavoriteSportChanges.invoke(activeSportEventId, true)
            } throws Exception()

            viewModel.action.test {
                // When
                viewModel.onActiveSportEventFavoriteChanged(
                    activeSportEventId, true
                )

                // Then
                assertEquals(
                    expected,
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onActiveSportEventFavoriteChanged Should send ShowErrorSnackBar When remove favorite fails`() {
        runTest {
            // Given
            val activeSportEventId = "459370"
            val expected = ActiveSportEventsAction.ShowErrorSnackBar(
                R.string.error_remove_from_favorite
            )
            coEvery {
                handleFavoriteSportChanges.invoke(activeSportEventId, false)
            } throws Exception()

            viewModel.action.test {
                // When
                viewModel.onActiveSportEventFavoriteChanged(
                    activeSportEventId, false
                )

                // Then
                assertEquals(
                    expected,
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `onSportEventFilterChanged Should filter events When is checked`() = runTest {
        val events = ActiveSportsEventsStubs.events
        coEvery {
            getActiveSportEvents.invoke(SPORT_EVENT_ID)
        } returns ActiveSportsEventsStubs.activeSportEvents
        val expected = ActiveSportEventsState(
            isLoading = false,
            error = null,
            sportEventId = SPORT_EVENT_ID,
            results = events
        )

        // When
        viewModel.onSportEventFilterChanged(
            sportEventId = SPORT_EVENT_ID,
            isChecked = true
        )

        // Then
        assertEquals(
            expected,
            viewModel.state.value
        )
        coEvery {
            getActiveSportEvents.invoke(SPORT_EVENT_ID)
        }
    }
}