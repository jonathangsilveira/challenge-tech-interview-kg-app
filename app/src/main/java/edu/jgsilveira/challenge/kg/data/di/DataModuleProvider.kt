package edu.jgsilveira.challenge.kg.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import edu.jgsilveira.challenge.kg.data.local.InMemoryFavoriteSportEventLocalDataSource
import edu.jgsilveira.challenge.kg.data.mapper.ActiveEventMapper
import edu.jgsilveira.challenge.kg.data.mapper.SportEventsMapper
import edu.jgsilveira.challenge.kg.data.remote.SportEventsApiService
import edu.jgsilveira.challenge.kg.data.remote.source.SportEventsRemoteDataSource
import edu.jgsilveira.challenge.kg.data.remote.source.SportEventsRemoteDataSourceImpl
import edu.jgsilveira.challenge.kg.data.repository.FavoriteSportsRepositoryImpl
import edu.jgsilveira.challenge.kg.data.repository.SportEventsRepositoryImpl
import edu.jgsilveira.challenge.kg.domain.repository.FavoriteSportsRepository
import edu.jgsilveira.challenge.kg.domain.repository.SportEventsRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val APP_JSON_MEDIA_TYPE = "application/json"
private const val MOCKAPI_BASE_URL = ""

private const val DATASTORE_FILE_NAME = "user_preferences"

@OptIn(ExperimentalSerializationApi::class)
object DataModuleProvider {

    val dataModule = module {
        single<Json> {
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        }

        single<OkHttpClient> {
            OkHttpClient.Builder()
                .readTimeout(15L, TimeUnit.SECONDS)
                .connectTimeout(15L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
        }

        factory {
            Retrofit.Builder()
                .baseUrl(MOCKAPI_BASE_URL)
                .client(get())
                .addConverterFactory(
                    get<Json>().asConverterFactory(
                        APP_JSON_MEDIA_TYPE.toMediaType()
                    )
                )
                .build()
        }

        factory<SportEventsRemoteDataSource> {
            SportEventsRemoteDataSourceImpl(
                sportEventsApi = get<Retrofit>().create(
                    SportEventsApiService::class.java
                ),
                sportEventsMapper = SportEventsMapper(
                    ActiveEventMapper()
                )
            )
        }

        factory<SportEventsRepository> {
            SportEventsRepositoryImpl(
                remoteDataSource = get()
            )
        }

        factory<FavoriteSportsRepository> {
            FavoriteSportsRepositoryImpl(
                localDataSource = InMemoryFavoriteSportEventLocalDataSource()
            )
        }

        single<DataStore<Preferences>> {
            val context = androidContext()
            PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile(DATASTORE_FILE_NAME)
            }
        }
    }
}