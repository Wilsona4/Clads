package com.decagonhq.clads.data.remote.profile

import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class UserProfileDTOMapper @Inject constructor() : DomainMapper<UserProfileDTO, UserProfile> {
    override fun mapToDomainModel(model: UserProfileDTO): UserProfile {
        return UserProfile(
            country = model.country,
            email = model.email,
            firstName = model.firstName,
            gender = model.gender,
            id = model.id,
            lastName = model.lastName,
            phoneNumber = model.phoneNumber,
            role = model.role,
            showroomAddress = model.showroomAddress,
            thumbnail = model.thumbnail,
            union = model.union,
            workshopAddress = model.workshopAddress
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): UserProfileDTO {
        return UserProfileDTO(
            country = domainModel.country,
            email = domainModel.email,
            firstName = domainModel.firstName,
            gender = domainModel.gender,
            id = domainModel.id,
            lastName = domainModel.lastName,
            phoneNumber = domainModel.phoneNumber,
            role = domainModel.role,
            showroomAddress = domainModel.showroomAddress,
            thumbnail = domainModel.thumbnail,
            union = domainModel.union,
            workshopAddress = domainModel.workshopAddress
        )
    }
}
