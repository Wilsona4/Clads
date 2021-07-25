package com.decagonhq.clads.data.local

import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class ClientEntityMapper @Inject constructor() :
    DomainMapper<ClientEntity, Client> {

    override fun mapToDomainModel(model: ClientEntity): Client {
        return Client(
            email = model.email,
            gender = model.gender,
            fullName = model.fullName,
            phoneNumber = model.phoneNumber,
            deliveryAddresses = model.deliveryAddresses,
            measurements = model.measurements,
            id = model.id,
            artisanId = model.artisanId
        )
    }

    override fun mapFromDomainModel(domainModel: Client): ClientEntity {
        return ClientEntity(
            email = domainModel.email,
            gender = domainModel.gender,
            fullName = domainModel.fullName,
            phoneNumber = domainModel.phoneNumber,
            deliveryAddresses = domainModel.deliveryAddresses,
            measurements = domainModel.measurements,
            artisanId = domainModel.artisanId!!,
            id = domainModel.id!!
        )
    }

    fun mapToDomainModelList(model: List<ClientEntity>): List<Client> {
        return model.map { mapToDomainModel(it) }
    }
    fun mapFromDomainModelList(domainModel: List<Client>): List<ClientEntity> {
        return domainModel.map { mapFromDomainModel(it) }
    }
}
