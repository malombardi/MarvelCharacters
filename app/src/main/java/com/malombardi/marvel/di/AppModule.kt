package com.malombardi.marvel.di

import com.malombardi.marvel.data.LocalDataSourceImpl
import com.malombardi.marvel.data.RemoteDataSourceImpl
import com.malombardi.marvel.data.db.MarvelCharacterDataBase
import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.repository.network.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(webService: WebService): RemoteDataSource {
        return RemoteDataSourceImpl(webService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(marvelCharacterDataBase: MarvelCharacterDataBase): LocalDataSource {
        return LocalDataSourceImpl(marvelCharacterDataBase)
    }
}
