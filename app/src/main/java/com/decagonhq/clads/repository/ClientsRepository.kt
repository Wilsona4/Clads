package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
    suspend fun getClients(): Flow<Resource<List<Client>>>

    suspend fun addClientToServer(client: Client): Flow<Resource<Client>>

    suspend fun deleteClient(clientId: Int): Flow<Resource<GenericResponseClass<List<Client>>>>

    suspend fun deleteClientFromDb(clients: List<Client>): Resource<Int>

    suspend fun addClientToDb(client: Client): Resource<Client>
}
