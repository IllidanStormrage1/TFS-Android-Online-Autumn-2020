package com.zkv.tfsfeed.data.dto.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class ProfileResponse(
    @SerializedName("about") val about: String?,
    @SerializedName("bdate") val bdate: String?,
    @SerializedName("career") val career: List<Career?>?,
    @SerializedName("domain") val domain: String,
    @SerializedName("faculty_name") val facultyName: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("last_seen") val lastSeen: LastSeen,
    @SerializedName("photo_100") val photo: String,
    @SerializedName("followers_count") val followersCount: Int?,
    @SerializedName("university_name") val universityName: String?,
    @SerializedName("home_town") val homeTown: String?,
    @SerializedName("online") val online: Int,
    @SerializedName("relation") val relation: Int,
    @SerializedName("city") val city: City?,
)
