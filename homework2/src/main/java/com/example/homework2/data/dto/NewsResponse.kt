package com.example.homework2.data.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class NewsResponse(
    @SerializedName("response")
    val response: Response
)

@Keep
class Response(
    @SerializedName("groups")
    val groups: List<Group>,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("next_from")
    val nextFrom: String,
    @SerializedName("profiles")
    val profiles: List<Profile>
)

@Keep
class Group(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_admin")
    val isAdmin: Int,
    @SerializedName("is_advertiser")
    val isAdvertiser: Int,
    @SerializedName("is_closed")
    val isClosed: Int,
    @SerializedName("is_member")
    val isMember: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("photo_200")
    val photo200: String,
    @SerializedName("photo_50")
    val photo50: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName("type")
    val type: String
)

@Keep
class Item(
    @SerializedName("attachments")
    val attachments: List<Attachment>?,
    @SerializedName("can_doubt_category")
    val canDoubtCategory: Boolean,
    @SerializedName("can_set_category")
    val canSetCategory: Boolean,
    @SerializedName("comments")
    val comments: Comments,
    @SerializedName("date")
    val date: Int,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    @SerializedName("likes")
    val likes: Likes,
    @SerializedName("marked_as_ads")
    val markedAsAds: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("post_source")
    val postSource: PostSource,
    @SerializedName("post_type")
    val postType: String,
    @SerializedName("reposts")
    val reposts: Reposts,
    @SerializedName("source_id")
    val sourceId: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("views")
    val views: Views
)

@Keep
class Attachment(
    @SerializedName("photo")
    val photo: Photo?,
    @SerializedName("link")
    val link: Link?,
    @SerializedName("type")
    val type: String
)

@Keep
class Link(
    @SerializedName("caption")
    val caption: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    @SerializedName("photo")
    val photo: Photo,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)

@Keep
class Photo(
    @SerializedName("album_id")
    val albumId: Int,
    @SerializedName("date")
    val date: Int,
    @SerializedName("has_tags")
    val hasTags: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("sizes")
    val sizes: List<Size>,
    @SerializedName("text")
    val text: String,
    @SerializedName("user_id")
    val userId: Int
)

@Keep
class Size(
    @SerializedName("height")
    val height: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)


@Keep
class Comments(
    @SerializedName("can_post")
    val canPost: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("groups_can_post")
    val groupsCanPost: Boolean
)

@Keep
class Likes(
    @SerializedName("can_like")
    val canLike: Int,
    @SerializedName("can_publish")
    val canPublish: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("user_likes")
    val userLikes: Int
)

@Keep
class PostSource(
    @SerializedName("type")
    val type: String
)

@Keep
class Reposts(
    @SerializedName("count")
    val count: Int,
    @SerializedName("user_reposted")
    val userReposted: Int
)

@Keep
class Views(
    @SerializedName("count")
    val count: Int
)

@Keep
class Profile(
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("online")
    val online: Int,
    @SerializedName("online_info")
    val onlineInfo: OnlineInfo,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("photo_50")
    val photo50: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName("sex")
    val sex: Int
)

@Keep
class OnlineInfo(
    @SerializedName("is_mobile")
    val isMobile: Boolean,
    @SerializedName("is_online")
    val isOnline: Boolean,
    @SerializedName("visible")
    val visible: Boolean
)
