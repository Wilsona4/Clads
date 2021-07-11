package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.local.ClientEntity
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {
    suspend fun getClients(): Flow<Resource<List<Client>>>
//    suspend fun getClient(clientId:Int): Flow<Resource<Client>>
    suspend fun addClient(client:Client):Flow<Resource<List<Client>>>

}