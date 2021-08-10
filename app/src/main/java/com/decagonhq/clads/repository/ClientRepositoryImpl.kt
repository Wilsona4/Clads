package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: CladsDatabase
) : ClientsRepository, SafeApiCall() {

    override suspend fun getClients(): Flow<Resource<List<Client>>> =

        networkBoundResource(
            fetchFromLocal = {
                database.clientDao().readAllClients()
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                apiService.getClients()
            },
            saveToLocalDB = { clientList ->
                database.withTransaction {
                    database.clientDao().deleteAllClients()
                    for (client in clientList.payload) {
                        database.clientDao().addClient(client)
                    }
                }
            }
        )

    override suspend fun getLocalDatabaseClient(): Flow<Resource<List<Client>>> {
        return database.clientDao().readAllClients().map {
            Resource.Success(it)
        }
    }

    override suspend fun addClient(client: Client): Flow<Resource<List<Client>>> =

        networkBoundResource(
            fetchFromLocal = {
                database.clientDao().readAllClients()
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                apiService.addClient(client)
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.clientDao().addClient(
                        it.payload
                    )
                }
            }
        )

    override suspend fun deleteClient(clientId: Int): Flow<Resource<List<Client>>> =
        networkBoundResource(
            fetchFromLocal = {
                database.clientDao().readAllClients()
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                apiService.deleteClient(clientId)
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.clientDao().deleteClient(clientId)
                }
            }
        )

    override suspend fun updateClient(clientId: Int, client: Client): Flow<Resource<List<Client>>> =

        networkBoundResource(

            fetchFromLocal = {
                database.clientDao().readAllClients()
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                apiService.updateClient(clientId.toString(), client)
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.clientDao().deleteClient(clientId)
                    database.clientDao().addClient(client)
                }
            }
        )

    override suspend fun getSingleClient(clientId: Int): Flow<Resource<Client>> {
        TODO("Not yet implemented")
    }
}
