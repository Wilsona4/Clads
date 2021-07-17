package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.local.ClientEntityMapper
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall

class ClientsRepositoryImpl(
    private val apiService: ApiService,
    private val clientEntityMapper: ClientEntityMapper,
    private val database: CladsDatabase
): ClientsRepository, SafeApiCall() {

    override suspend fun addClient(client: Client) {
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
    }

//    override suspend fun getClients(): Flow<Resource<List<Client>>> =
//        networkBoundResource(
//            fetchFromLocal = {
//                database.clientDao().readClients().map {
//                    clientEntityMapper.mapToDomainModel(it)
//                }
//            },
//            shouldFetchFromRemote = {
//                true
//            },
//            fetchFromRemote = {
//                delay(2000)
//                apiService.getClients()
//            },
//            saveToLocalDB = {
//                database.withTransaction {
//                    database.clientDao().deleteClients()
//                    database.clientDao().addClients(
//                        clientEntityMapper.mapFromDomainModel(it.payload)
//                    )
//                }
//            }
//        )


}
