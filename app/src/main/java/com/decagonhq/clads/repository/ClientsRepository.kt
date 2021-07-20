package com.decagonhq.clads.repository





import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
    suspend fun getClients(): Flow<Resource<List<Client>>>

    suspend fun addClientToServer(client: Client): Resource<GenericResponseClass<Client>>

    suspend fun deleteClient(clientId: Int): Resource<GenericResponseClass<List<Client>>>

    suspend fun deleteClientFromDb(clients: List<Client>): Resource<Int>

    suspend fun addClientToDb(clients: Client): Resource<Client>
}

