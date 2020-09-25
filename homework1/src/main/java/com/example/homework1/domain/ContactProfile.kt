package com.example.homework1.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ContactProfile(
    val name: String,
    val phoneNumbers: List<String>
) : Parcelable