package com.zkv.tfsfeed.data.converter

import com.zkv.tfsfeed.data.dto.group.GroupInformationResponse
import com.zkv.tfsfeed.data.dto.profile.ProfileResponse
import com.zkv.tfsfeed.domain.model.Profile
import com.zkv.tfsfeed.domain.utils.getTimeSpan

object ProfileResponseConverter {

    fun Profile(
        profileResponse: ProfileResponse,
        groupInformationResponse: GroupInformationResponse?,
    ): Profile = with(profileResponse) {
        Profile(
            nickname = "$firstName $lastName",
            lastSeenStatus = getTimeSpan(lastSeen.time * 1000L),
            userId = domain,
            avatarUrl = photo,
            about = about ?: "",
            followers = followersCount ?: 0,
            bdate = bdate ?: "",
            homeTown = homeTown ?: "",
            career = "${groupInformationResponse?.name ?: ""} ${career?.firstOrNull()?.position ?: ""}",
            education = "$universityName $facultyName",
            online = online == 1,
            relation = relation,
            city = city?.title ?: ""
        )
    }
}