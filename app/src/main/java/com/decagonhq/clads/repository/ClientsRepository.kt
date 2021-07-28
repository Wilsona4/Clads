package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
    suspend fun getClients(): Flow<Resource<List<Client>>>

    suspend fun getLocalDatabaseClient(): Flow<Resource<List<Client>>>

    suspend fun addClient(client: Client): Flow<Resource<List<Client>>>

    suspend fun deleteClient(clientId: Int): Flow<Resource<List<Client>>>

    suspend fun getSingleClient(clientId: Int): Flow<Resource<Client>>
}
