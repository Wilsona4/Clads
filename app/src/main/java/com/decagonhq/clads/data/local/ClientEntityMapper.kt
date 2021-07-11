package com.decagonhq.clads.data.local

import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class ClientEntityMapper @Inject constructor() :
    DomainMapper<List<ClientEntity>, List<Client>> {

    override fun mapToDomainModel(model: List<ClientEntity>):List<Client> {
        val clientList = mutableListOf<Client>()
        for(clientEntity in model){
            clientList.add(Client(
                email = clientEntity.email,
                gender = clientEntity.gender,
                fullName = clientEntity.fullName,
                phoneNumber = clientEntity.phoneNumber,
                deliveryAddresses = clientEntity.deliveryAddresses,
                measurements =clientEntity.measurements,
                id =  clientEntity.id,
                artisanId = clientEntity.artisanId
            ))
        }
        return clientList
    }

    override fun mapFromDomainModel(domainModel: List<Client>): List<ClientEntity> {
        val clientEntityList = mutableListOf<ClientEntity>()
        for(client in domainModel){
            clientEntityList.add(ClientEntity(
                email = client.email,
                gender = client.gender,
                fullName = client.fullName,
                phoneNumber = client.phoneNumber,
                deliveryAddresses = client.deliveryAddresses,
                measurements =client.measurements,
                id = client.id,
                artisanId = client.artisanId
            ))
        }
        return clientEntityList
    }
}