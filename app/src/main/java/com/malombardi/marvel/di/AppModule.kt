package com.malombardi.marvel.di

import android.content.Context
import androidx.room.Room
import com.malombardi.data.ErrorHandler
import com.malombardi.data.LocalDataSourceImpl
import com.malombardi.data.RemoteDataSourceImpl
import com.malombardi.data.db.MarvelCharacterDataBase
import com.malombardi.data.db.dao.MarvelDao
import com.malombardi.domain.datasources.LocalDataSource
import com.malombardi.domain.datasources.RemoteDataSource
import com.malombardi.domain.errors.IErrorHandler
import com.malombardi.domain.repository.Repository
import com.malombardi.data.network.WebService
import com.malombardi.domain.usecases.GetCharactersUseCase
import com.malombardi.domain.usecases.GetComicsUseCase
import com.malombardi.domain.usecases.SearchCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
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

    @Singleton
    @Provides
    fun provideErrorHandler(): IErrorHandler {
        return ErrorHandler()
    }

    @Singleton
    @Provides
    fun provideRepository(localDataSource: LocalDataSourceImpl, remoteDataSource: RemoteDataSourceImpl): Repository{
        return Repository(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideCharacterUseCase(repository: Repository,
                                @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
                                errorHandler: IErrorHandler): GetCharactersUseCase {
        return GetCharactersUseCase(repository, coroutineDispatcher, errorHandler)
    }

    @Provides
    fun provideComicsUseCase(repository: Repository,
                                @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
                                errorHandler: IErrorHandler): GetComicsUseCase {
        return GetComicsUseCase(repository, coroutineDispatcher, errorHandler)
    }

    @Provides
    fun provideSearchCharacterUseCase(repository: Repository,
                             @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
                             errorHandler: IErrorHandler): SearchCharactersUseCase {
        return SearchCharactersUseCase(repository, coroutineDispatcher, errorHandler)
    }

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
