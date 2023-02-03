package com.ariefrahmanfajri.pilem.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ariefrahmanfajri.pilem.data.repository.MovieRepository
import com.ariefrahmanfajri.pilem.data.reqres.local.LocalDataSource
import com.ariefrahmanfajri.pilem.data.reqres.local.PilemDatabase
import com.ariefrahmanfajri.pilem.data.reqres.network.MovieApi
import com.ariefrahmanfajri.pilem.data.reqres.network.MovieApiClient
import com.ariefrahmanfajri.pilem.domain.repository.IMovieRepository
import com.ariefrahmanfajri.pilem.domain.usecase.MovieInteractor
import com.ariefrahmanfajri.pilem.domain.usecase.MovieUseCase
import com.ariefrahmanfajri.pilem.util.GenresIdConverter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule(private val application: Application) {
    private val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideMovieApiClient(): MovieApiClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
            .create(MovieApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(): PilemDatabase {
        return Room.databaseBuilder(
            provideApplicationContext(),
            PilemDatabase::class.java,
            "pilem_db",
        )
            .addTypeConverter(GenresIdConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSource(provideDatabase().movieDao())
    }

    @Provides
    fun provideMovieApi(): MovieApi {
        return MovieApi(provideMovieApiClient())
    }

    @Provides
    fun provideMovieRepository(): IMovieRepository {
        return MovieRepository(provideMovieApiClient(), provideMovieApi(), provideLocalDataSource())
    }

    @Provides
    fun provideMovieUseCase(): MovieUseCase {
        return MovieInteractor(provideMovieRepository())
    }
}