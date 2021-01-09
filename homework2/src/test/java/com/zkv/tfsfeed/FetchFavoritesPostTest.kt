package com.zkv.tfsfeed

import com.zkv.tfsfeed.domain.middleware.FetchFavoritesPost
import com.zkv.tfsfeed.domain.middleware.FetchNewsFeed
import com.zkv.tfsfeed.domain.model.NewsItem
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalStdlibApi
@RunWith(MockitoJUnitRunner::class)
class FetchFavoritesPostTest {

    @Mock
    lateinit var fetchNewsFeed: FetchNewsFeed
    lateinit var fetchFavoritesPost: FetchFavoritesPost

    @Before
    fun init() {
        fetchFavoritesPost = FetchFavoritesPost(fetchNewsFeed)
    }

    @Test
    fun checkFetchFavoritesPosts() {
        `when`(fetchNewsFeed(false)).thenReturn(Single.just(fakeNews))
        assertEquals(fakeFavoritesNews, fetchFavoritesPost(false).blockingGet())
    }

    @Test
    fun checkFetchFavoritesPostsWithEmpty() {
        `when`(fetchNewsFeed(false)).thenReturn(Single.just(emptyList()))
        assertEquals(emptyList<NewsItem>(), fetchFavoritesPost(false).blockingGet())
    }

    @Test
    fun checkFetchFavoritesPostsWithEquals() {
        `when`(fetchNewsFeed(false)).thenReturn(Single.just(fakeFavoritesNews))
        assertEquals(fakeFavoritesNews, fetchFavoritesPost(false).blockingGet())
    }

    private val fakeNews = buildList {
        add(createNewsItem(0, 0))
        add(createNewsItem(1, 1))
        add(createNewsItem(2, 0))
        add(createNewsItem(3, 1))
        add(createNewsItem(4, 0))
        add(createNewsItem(5, 1))
        add(createNewsItem(6, 0))
    }

    private val fakeFavoritesNews = buildList {
        add(createNewsItem(0, 0))
        add(createNewsItem(2, 0))
        add(createNewsItem(4, 0))
        add(createNewsItem(6, 0))
    }

    private fun createNewsItem(id: Int, canLike: Int) =
        NewsItem("", "", "", 0L, "", 0, "", id, "", 0, 0, canLike, 0, "", false)
}
