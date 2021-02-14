package model

import com.google.gson.annotations.SerializedName

class IGTimelineMedia(
    @SerializedName("count")
    val count: Int,
    @SerializedName("page_info")
    val pageInfo: IGPageInfo,
    @SerializedName("edges")
    val imagePosts: List<IGImagePost>
) {
}


class IGPageInfo(
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("end_cursor")
    val endCursor: String
)

class IGImagePost(
    @SerializedName("display_url")
    val pictureUrl: String,
    @SerializedName("is_video")
    val isVideo: Boolean,
    @SerializedName("comments_disabled")
    val areCommentsDisabled: Boolean,
    @SerializedName("edge_media_to_comment")
    val commentCount: IGFigure,
    @SerializedName("taken_at_timestamp")
    val takenAtTimestamp: Int,
    @SerializedName("edge_liked_by")
    val likeCount: IGFigure,
    @SerializedName("edge_media_to_caption")
    val imageDescription: IGMediaDescription
){
    fun writeToCsv(userId:String): String {
        return "$userId;$pictureUrl;$isVideo;$areCommentsDisabled;${commentCount.count};$takenAtTimestamp;${likeCount.count};${imageDescription.text[0]}"
    }

    companion object {
        val HEADER = "userId;pictureUrl;isVideo;areCommentsDisabled;commentCount;takenAtTimestamp;likeCount;postText"
    }
}

class IGFigure(
    @SerializedName("count")
    val count: Int
)

class IGMediaDescription(
    @SerializedName("edges")
    val text: List<IGImageText>)

class IGImageText(
    @SerializedName("text")
    val text: String)

class IGMediaLocation(
    @SerializedName("location")
    val name: String
)