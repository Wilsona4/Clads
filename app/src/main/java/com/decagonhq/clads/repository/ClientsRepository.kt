package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.client.Client

interface ClientsRepository {

    suspend fun addClient(client: Client)
    //suspend fun getClients(): Flow<Resource<List<Client>>>
    //suspend fun getClient(clientId:Int): Flow<Resource<Client>>

    }
