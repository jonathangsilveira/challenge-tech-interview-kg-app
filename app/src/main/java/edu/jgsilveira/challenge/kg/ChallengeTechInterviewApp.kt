package edu.jgsilveira.challenge.kg

import android.app.Application
import edu.jgsilveira.challenge.kg.data.di.DataModuleProvider
import edu.jgsilveira.challenge.kg.domain.di.DomainModuleProvider
import edu.jgsilveira.challenge.kg.presentation.di.PresentationModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChallengeTechInterviewApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChallengeTechInterviewApp)
            modules(
                DataModuleProvider.dataModule,
                DomainModuleProvider.domainModule,
                PresentationModuleProvider.presentationModule
            )
        }
    }
}