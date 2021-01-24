package model

import com.google.gson.annotations.SerializedName

class IGUser(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("is_private")
    val isPrivate: Boolean,
    @SerializedName("edge_followed_by")
    val followerCount: IGFigure,
    @SerializedName("edge_follow")
    val followingCount: IGFigure,
    @SerializedName("profile_pic_url")
    val pictureUrl: String,
    @SerializedName("category_name")
    val category: String,
    @SerializedName("edge_owner_to_timeline_media")
    val igTimelineMedia: IGTimelineMedia
) {

}