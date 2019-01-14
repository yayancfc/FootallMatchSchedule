package com.yayanheryanto.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player (
    @SerializedName("strNationality")
    var strNationality: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("dateBorn")
    var dateBorn: String? = null,

    @SerializedName("dateSigned")
    var dateSigned: String? = null,

    @SerializedName("strSigning")
    var strSigning: String? = null,

    @SerializedName("strBirthLocation")
    var strBirthLocation: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null,

    @SerializedName("strPosition")
    var strPosition: String? = null,

    @SerializedName("strHeight")
    var strHeight: String? = null,

    @SerializedName("strWeight")
    var strWeight: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strThumb")
    var strThumb: String? = null
): Parcelable