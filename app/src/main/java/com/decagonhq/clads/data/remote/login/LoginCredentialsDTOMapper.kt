package com.decagonhq.clads.data.remote.login

import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class LoginCredentialsDTOMapper @Inject constructor() : DomainMapper<LoginCredentialsDTO, LoginCredentials> {
    override fun mapToDomainModel(model: LoginCredentialsDTO): LoginCredentials {
        return LoginCredentials(
            email = model.email,
            password = model.password
        )
    }

    override fun mapFromDomainModel(domainModel: LoginCredentials): LoginCredentialsDTO {
        return LoginCredentialsDTO(
            email = domainModel.email,
            password = domainModel.password
        )
    }
}
