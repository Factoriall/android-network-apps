package com.survivalcoding.network_apps.paging.data.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.survivalcoding.network_apps.paging.domain.model.Post
import kotlinx.coroutines.flow.Flow

class PostRemoteDataSource(private val postApi: PostApi) {
    fun getPostStream(): Flow<PagingData<Post>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { PostPagingSource(postApi) }
        ).flow
    }

    suspend fun getUserById(id: Int) = postApi.getUserById(id)

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}