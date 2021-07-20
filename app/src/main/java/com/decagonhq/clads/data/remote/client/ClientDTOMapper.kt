package com.decagonhq.clads.data.remote.client

import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class ClientDTOMapper @Inject constructor() : DomainMapper<ClientDTO, Client> {
    override fun mapToDomainModel(model: ClientDTO): Client {
        return Client(
            id = model.id,
            artisanId = model.artisanId,
            fullName = model.fullName,
            phoneNumber = model.phoneNumber,
            email = model.email,
            gender = model.gender,
            // deliveryAddresses = model.deliveryAddresses,
            // measurements = model.measurements
        )
    }

    override fun mapFromDomainModel(domainModel: Client): ClientDTO {
        return ClientDTO(
            // id = domainModel.id,
            // artisanId = domainModel.artisanId,
            fullName = domainModel.fullName,
            phoneNumber = domainModel.phoneNumber,
            email = domainModel.email,
            gender = domainModel.gender,
            // deliveryAddresses = domainModel.deliveryAddresses,
            // measurements = domainModel.measurements

        )
    }
}
