package com.ssmmhh.free2playgames.di

import com.ssmmhh.free2playgames.common.Constants.BASE_FREE_TO_GAME_URL
import com.ssmmhh.free2playgames.featureGame.data.remote.FreeToGameApi
import com.ssmmhh.free2playgames.featureGame.data.repository.GameRepositoryImpl
import com.ssmmhh.free2playgames.featureGame.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_FREE_TO_GAME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideFreeToGameApi(retrofit: Retrofit): FreeToGameApi {
        return retrofit.create(FreeToGameApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository =
        gameRepositoryImpl
}
