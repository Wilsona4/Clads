package com.decagonhq.clads.data.local

import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class UserProfileEntityMapper @Inject constructor() : DomainMapper<UserProfileEntity, UserProfile> {
    override fun mapToDomainModel(model: UserProfileEntity): UserProfile {
        return UserProfile(
            country = model.country,
            deliveryTime = model.deliveryTime,
            email = model.email,
            firstName = model.firstName,
            gender = model.gender,
            genderFocus = model.genderFocus,
            lastName = model.lastName,
            measurementOption = model.measurementOption,
            phoneNumber = model.phoneNumber,
            role = model.role,
            showroomAddress = model.showroomAddress,
            specialties = model.specialties,
            thumbnail = model.thumbnail,
            trained = model.trained,
            union = model.union,
            workshopAddress = model.workshopAddress,
            paymentOptions = model.paymentOptions,
            paymentTerms = model.paymentTerms
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): UserProfileEntity {
        return UserProfileEntity(
            country = domainModel.country,
            deliveryTime = domainModel.deliveryTime,
            email = domainModel.email,
            firstName = domainModel.firstName,
            gender = domainModel.gender,
            genderFocus = domainModel.genderFocus,
            lastName = domainModel.lastName,
            measurementOption = domainModel.measurementOption,
            phoneNumber = domainModel.phoneNumber,
            role = domainModel.role,
            showroomAddress = domainModel.showroomAddress,
            specialties = domainModel.specialties,
            thumbnail = domainModel.thumbnail,
            trained = domainModel.trained,
            union = domainModel.union,
            workshopAddress = domainModel.workshopAddress,
            paymentOptions = domainModel.paymentOptions,
            paymentTerms = domainModel.paymentTerms
        )
    }
}
