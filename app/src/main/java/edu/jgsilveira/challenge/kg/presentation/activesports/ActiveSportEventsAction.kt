package edu.jgsilveira.challenge.kg.presentation.activesports

internal sealed interface ActiveSportEventsAction {
    data class ShowErrorSnackBar(
        val resId: Int
    ) : ActiveSportEventsAction
}