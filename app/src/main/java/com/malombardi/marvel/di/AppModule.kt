package com.malombardi.marvel.di


import android.content.Context
import androidx.room.Room
import com.malombardi.marvel.data.LocalDataSourceImpl
import com.malombardi.marvel.data.RemoteDataSourceImpl
import com.malombardi.marvel.data.db.MarvelCharacterDataBase
import com.malombardi.marvel.data.db.dao.MarvelDao
import com.malombardi.marvel.domain.datasources.LocalDataSource
import com.malombardi.marvel.domain.datasources.RemoteDataSource
import com.malombardi.marvel.domain.repository.network.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMarvelCharacterDataBase(@ApplicationContext app: Context) : MarvelCharacterDataBase {
        return Room
            .databaseBuilder(app, MarvelCharacterDataBase::class.java,
                MarvelCharacterDataBase.DB_NAME
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideMarvelDao(db: MarvelCharacterDataBase) : MarvelDao {
        return db.marvelDao()
    }

    @Singleton
    @Provides
    fun provideWebService(): WebService {
        return WebService()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(webService: WebService): RemoteDataSource {
        return RemoteDataSourceImpl(webService)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: MarvelDao): LocalDataSource {
        return LocalDataSourceImpl(dao)
    }
}
