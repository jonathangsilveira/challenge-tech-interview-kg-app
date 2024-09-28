package edu.jgsilveira.challenge.kg.presentation.activesports

import edu.jgsilveira.challenge.kg.R

internal enum class ActiveSportEventsUIError(
    val messageResId: Int
) {
    GENERIC(R.string.something_went_wrong),
    NO_DATA_FOUND(R.string.no_data_found),
    NETWORK_ERROR(R.string.network_error)
}