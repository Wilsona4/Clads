package com.decagonhq.clads.data.remote.registration

import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class UserRegDTOMapper @Inject constructor() : DomainMapper<UserRegistrationDTO, UserRegistration> {
    override fun mapToDomainModel(model: UserRegistrationDTO): UserRegistration {
        return UserRegistration(
            country = model.country,
            email = model.email,
            firstName = model.firstName,
            lastName = model.lastName,
            gender = model.gender,
            category = model.category,
            deliveryAddress = model.deliveryAddress,
            password = model.password,
            phoneNumber = model.phoneNumber,
            role = model.role,
            thumbnail = model.thumbnail
        )
    }

    override fun mapFromDomainModel(domainModel: UserRegistration): UserRegistrationDTO {
        return UserRegistrationDTO(
            country = domainModel.country,
            email = domainModel.email,
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            gender = domainModel.gender,
            category = domainModel.category,
            deliveryAddress = domainModel.deliveryAddress,
            password = domainModel.password,
            phoneNumber = domainModel.phoneNumber,
            role = domainModel.role,
            thumbnail = domainModel.thumbnail
        )
    }
}
