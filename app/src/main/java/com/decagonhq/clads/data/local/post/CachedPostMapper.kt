package com.decagonhq.clads.data.local.post

import com.decagonhq.clads.data.remote.Post
import com.decagonhq.clads.util.DomainMapper
import javax.inject.Inject

class CachedPostMapper @Inject constructor() : DomainMapper<PostEntity, Post> {
    override fun mapToDomainModel(model: PostEntity): Post {
        return Post(
            userId = model.userId,
            id = model.id,
            title = model.title,
            body = model.body
        )
    }

    override fun mapFromDomainModel(domainModel: Post): PostEntity {
        return PostEntity(
            userId = domainModel.userId,
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun mapFromEntityList(entities: List<PostEntity>): List<Post> {
        return entities.map { mapToDomainModel(it) }
    }

    fun mapToEntityList(domainModel: List<Post>): List<PostEntity> {
        return domainModel.map { mapFromDomainModel(it) }
    }
}
