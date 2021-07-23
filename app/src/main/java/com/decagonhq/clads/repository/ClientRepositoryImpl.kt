package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.local.ClientEntityMapper
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.util.Event
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
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

    override suspend fun addClientToServer(client: Client): Resource<Event<GenericResponseClass<Client>>> {

        return safeApiCall {
            Event(apiService.addClient(client))
        }
    }

    override suspend fun deleteClient(clientId: Int): Resource<GenericResponseClass<Client>> =

        safeApiCall {
            apiService.deleteClient(clientId)
        }

    override suspend fun deleteClientFromDb(client: Client): Resource<Int> {

        val clientEntityMapped = clientEntityMapper.mapFromDomainModel(convertToList(client))

        return safeApiCall { database.clientDao().deleteClient(clientEntityMapped[0]) }
    }

    override suspend fun addClientToDb(client: Client): Resource<Event<Client>> {

        val clientList = mutableListOf<Client>()
        clientList.add(client)
        val result = safeApiCall {

            database.clientDao().addClient(clientEntityMapper.mapFromDomainModel(clientList)[0])
        }

        return if (result.data!! > 0) {
            Resource.Success(data = Event(client), message = "Client added successfully")
        } else {
            Resource.Error(
                isNetworkError = false,
                errorBody = null,
                data = null,
                message = result.message
            )
        }
    }

    private fun convertToList(client: Client): List<Client> {
        val clientList = mutableListOf<Client>()
        clientList.add(client)
        return clientList
    }
}
