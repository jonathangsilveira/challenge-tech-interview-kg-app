package edu.jgsilveira.challenge.kg.presentation.activesports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.jgsilveira.challenge.kg.R
import edu.jgsilveira.challenge.kg.presentation.mapper.toUIError
import edu.jgsilveira.challenge.kg.presentation.mapper.toViewData
import edu.jgsilveira.challenge.kg.presentation.model.SportEventViewData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ActiveSportEventsViewModel(
    private val useCaseProvider: ActiveSportEventsUseCaseProvider,
) : ViewModel() {
    private val mutableState = MutableStateFlow(ActiveSportEventsState())
    val state: StateFlow<ActiveSportEventsState> = mutableState

    private val mutableAction = MutableSharedFlow<ActiveSportEventsAction>(
        replay = 0
    )
    val action: SharedFlow<ActiveSportEventsAction> = mutableAction

    fun refreshSportEvents() {
        getActiveSportEvents(
            sportEventId = state.value.sportEventId
        )
    }

    private fun getActiveSportEvents(sportEventId: String?) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    sportEventId = sportEventId
                )
            }
            runCatching {
                useCaseProvider.getActiveSportEvents(sportEventId)
            }.mapCatching {
                val favoriteSportEventIds = useCaseProvider.getFavoriteSportEventIds()
                it.toViewData(
                    favoriteActiveSportEventIds = favoriteSportEventIds,
                    filteredSportEventId = sportEventId
                )
            }.onSuccess { sportEventViewData ->
                handleSuccess(sportEventViewData)
            }.onFailure { cause ->
                handleError(cause)
            }
        }
    }

    fun onActiveSportEventFavoriteChanged(
        activeSportEventId: String,
        isFavorite: Boolean
    ) {
        viewModelScope.launch {
            runCatching {
                useCaseProvider.handleFavoriteActiveSportEventChanges(
                    activeSportEventId, isFavorite
                )
            }.onFailure {
                handleIntentError(isFavorite)
            }
        }
    }

    fun onSportEventFilterChanged(
        sportEventId: String,
        isChecked: Boolean
    ) {
        if (isChecked) {
            getActiveSportEvents(sportEventId)
        } else {
            getActiveSportEvents(null)
        }
    }

    private fun handleError(exception: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                error = exception.toUIError()
            )
        }
    }

    private suspend fun handleIntentError(
        isFavorite: Boolean
    ) {
        val messageResId = if (isFavorite) {
            R.string.error_add_to_favorite
        } else {
            R.string.error_remove_from_favorite
        }
        sendAction(
            ActiveSportEventsAction.ShowErrorSnackBar(
                resId = messageResId
            )
        )
    }

    private fun handleSuccess(activeSportEvents: List<SportEventViewData>) {
        updateState {
            it.copy(
                isLoading = false,
                results = activeSportEvents,
                error = null
            )
        }
    }

    private fun updateState(
        newState: (ActiveSportEventsState) -> ActiveSportEventsState
    ) {
        mutableState.update { currentState ->
            newState(currentState)
        }
    }

    private suspend fun sendAction(action: ActiveSportEventsAction) {
        mutableAction.emit(action)
    }
}