package com.decagonhq.clads.data.remote.images

import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class ImageDTOMapper @Inject constructor() : DomainMapper<ImageDTO, UserProfileImage> {
    override fun mapToDomainModel(model: ImageDTO): UserProfileImage {
        return UserProfileImage(
            downloadUri = model.downloadUri,
            fileId = model.fileId,
            fileName = model.fileName,
            fileType = model.fileType,
            uploadStatus = model.uploadStatus
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfileImage): ImageDTO {
        return ImageDTO(
            downloadUri = domainModel.downloadUri,
            fileId = domainModel.fileId,
            fileName = domainModel.fileName,
            fileType = domainModel.fileType,
            uploadStatus = domainModel.uploadStatus

        )
    }
}
