package com.decagonhq.clads.data.remote

import com.decagonhq.clads.data.domain.images.ImageModel
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class ImageDTOMapper @Inject constructor() : DomainMapper<ImageDTO, ImageModel> {
    override fun mapToDomainModel(model: ImageDTO): ImageModel {
        return ImageModel(
            downloadUri = model.downloadUri,
            fileId = model.fileId,
            fileName = model.fileName,
            fileType = model.fileType,
            uploadStatus = model.uploadStatus
        )
    }

    override fun mapFromDomainModel(domainModel: ImageModel): ImageDTO {
        return ImageDTO(
            downloadUri = domainModel.downloadUri,
            fileId = domainModel.fileId,
            fileName = domainModel.fileName,
            fileType = domainModel.fileType,
            uploadStatus = domainModel.uploadStatus
        )
    }
}