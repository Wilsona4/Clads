package com.decagonhq.clads.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T, A> tripleThreat(
    performNetworkCall: suspend () -> Resource<T>,
    cacheToDatabase: suspend (A) -> Unit?,
    queryDatabase: suspend () -> Flow<A>?
): Flow<Resource<T>> = flow {
    emit(Resource.Loading("Loading"))
    val networkResponse = performNetworkCall.invoke()
    emit(networkResponse)

//    val cachedToDatabase = cacheToDatabase.invoke()
}

// private fun <P1, R, T> (suspend (P1) -> R).invoke(networkResponse: Resource<T>): Unit? {
//
// }

// suspend fun <T, A> performOperation(
//    performNetworkCall: () -> Resource<T>?,
//    cacheToDatabase: suspend (A) -> Unit?,
//    queryDatabase: suspend () -> Flow<T>?,
// ): Flow<Resource<T>> = flow {
//    emit(Resource.Loading)
//    try {
//        val networkResponse = performNetworkCall.invoke()
//        if (networkResponse.isSuccessful) {
//            val networkResult = Resource.Success(networkResponse)
//            networkResult.data?.body()?.let { cacheToDatabase.invoke() }
//        }
//        queryDatabase.invoke()?.collect {
//            emit(Resource.Success(it))
//        }
//    } catch (e: Exception) {
//        emit(Resource.Error(e))
//    }
// }
