package edu.jgsilveira.challenge.kg.presentation.di

import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsUseCaseProvider
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModuleProvider {

    val presentationModule = module {
        viewModel {
            ActiveSportEventsViewModel(
                useCaseProvider = ActiveSportEventsUseCaseProvider(
                    getActiveSportEvents = get(),
                    handleFavoriteActiveSportEventChanges = get(),
                    getFavoriteSportEventIds = get()
                )
            )
        }
    }
}