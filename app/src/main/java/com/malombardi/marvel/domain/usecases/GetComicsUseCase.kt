package com.malombardi.marvel.domain.usecases

import com.malombardi.marvel.domain.Constants.CHARACTER_ID_KEY
import com.malombardi.marvel.domain.Constants.OFFSET_KEY
import com.malombardi.marvel.domain.errors.IErrorHandler
import com.malombardi.marvel.domain.models.MarvelComic
import com.malombardi.marvel.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(
    private val repository: Repository,
    coroutineDispatcher: CoroutineDispatcher,
    errorHandler: IErrorHandler
) : FlowUseCase<Map<String, String>, List<MarvelComic>>(coroutineDispatcher, errorHandler) {

    override suspend fun execute(parameters: Map<String, String>): Flow<List<MarvelComic>> {
        //here I need the characterId to search or throw an exception
        val result = repository.getCharacterComics(parameters.getValue(CHARACTER_ID_KEY))
        if (parameters[OFFSET_KEY] != null) {
            repository.checkComicsRequireNewPage(
                parameters[CHARACTER_ID_KEY]!!,
                parameters[OFFSET_KEY]!!.toInt()
            )
        }
        return result
    }
}
