package com.zkv.tfsfeed

import com.zkv.tfsfeed.data.repository.LocalRepositoryImpl
import com.zkv.tfsfeed.data.repository.MediatorRepositoryImpl
import com.zkv.tfsfeed.data.repository.RemoteRepositoryImpl
import com.zkv.tfsfeed.domain.middleware.FetchNewsFeed
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.domain.repository.MediatorRepository
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchNewsFeedTests {

    @Mock
    lateinit var localRepositoryImpl: LocalRepositoryImpl

    @Mock
    lateinit var remoteRepositoryImpl: RemoteRepositoryImpl

    lateinit var mediatorRepository: MediatorRepository
    lateinit var fetchNewsFeed: FetchNewsFeed

    @Before
    fun init() {
        mediatorRepository = MediatorRepositoryImpl(localRepositoryImpl, remoteRepositoryImpl)
        fetchNewsFeed = FetchNewsFeed(mediatorRepository)

        `when`(localRepositoryImpl.fetchNewsFeedPosts()).thenReturn(
            Single.just(listOf(createNewsItem(0, 0)))
        )
        `when`(remoteRepositoryImpl.fetchAllPosts()).thenReturn(
            Single.just(listOf(createNewsItem(1, 1), createNewsItem(2, 0)))
        )
    }

    @Test
    fun checkLocalRepository() {
        val result = fetchNewsFeed(false).blockingGet()
        assertEquals(listOf(createNewsItem(0, 0)), result)
    }

    @Test
    fun checkRemoteRepositoryWithRefresh() {
        val result = fetchNewsFeed(true).blockingGet()
        assertEquals(listOf(createNewsItem(1, 1), createNewsItem(2, 0)), result)
    }

    @Test
    fun checkRewriteNewsFeedTableInvoke() {
        val result = fetchNewsFeed(true).blockingGet()
        verify(localRepositoryImpl, times(1)).rewriteNewsFeedTable(result)
    }

    @Test
    fun checkFetchAllPostsInvoke() {
        fetchNewsFeed(true).blockingGet()
        verify(remoteRepositoryImpl, times(1)).fetchAllPosts()
    }

    private fun createNewsItem(id: Int, canLike: Int) =
        NewsItem("", "", "", 0L, "", 0, "", id, "", 0, 0, canLike, 0, "", false)
}
