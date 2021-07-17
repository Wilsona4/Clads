package com.decagonhq.clads.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Response

inline fun <DB, REMOTE> networkBoundResource(
    crossinline fetchFromLocal: suspend () -> Flow<DB>,
    crossinline fetchFromRemote: suspend () -> REMOTE,
    crossinline saveToLocalDB: suspend (REMOTE) -> Unit,
    crossinline shouldFetchFromRemote: (DB) -> Boolean = { true }
) = flow {

    val localData = fetchFromLocal().first()

    val dataStream = if (shouldFetchFromRemote(localData)) {
        emit(Resource.Loading(localData, "Loading"))
        try {
            saveToLocalDB(fetchFromRemote())

            fetchFromLocal().map { dbData ->
                Resource.Success(dbData)
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    fetchFromLocal().map {
                        Resource.Error(
                            false,
                            throwable.response() as Response<Any>, it
                        )
                    }
                }
                else -> {
                    fetchFromLocal().map {
                        Resource.Error(
                            true,
                            null, it
                        )
                    }
                }
            }
        }
    } else {
        fetchFromLocal().map { Resource.Success(it) }
    }
    emitAll(dataStream)
}
