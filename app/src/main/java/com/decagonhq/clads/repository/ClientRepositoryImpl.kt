package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.local.ClientEntityMapper
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.util.RateLimiter
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

import kotlinx.coroutines.flow.map

class ClientRepositoryImpl(
    private val apiService: ApiService,
    private val clientEntityMapper: ClientEntityMapper,
    private val database: CladsDatabase
) : ClientsRepository, SafeApiCall() {

    override suspend fun getClients(): Flow<Resource<List<Client>>> =
        networkBoundResource(
            fetchFromLocal = {
                database.clientDao().readClients().map {
                    clientEntityMapper.mapToDomainModel(it)
                }
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                delay(2000)
                apiService.getClients()
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.clientDao().deleteClients()
                    database.clientDao().addClients(
                        clientEntityMapper.mapFromDomainModel(it.payload)
                    )
                }
            }
        )

    override suspend fun addClient(client: Client): Flow<Resource<List<Client>>> {

        val response = safeApiCall {
            apiService.addClient(client)
        }
        if (response is Resource.Success) {

            val clientList = mutableListOf<Client>()
            database.withTransaction {
                response.data?.payload?.let {
                    clientList.add(it)
                    clientEntityMapper.mapFromDomainModel(clientList)
                }
                    ?.let {
                        database.clientDao().addClients(
                            it
                        )
                    }
            }

        }
        return database.clientDao().readClients().map {
            Resource.Success(ClientEntityMapper().mapToDomainModel(it))
        }
    }


}




