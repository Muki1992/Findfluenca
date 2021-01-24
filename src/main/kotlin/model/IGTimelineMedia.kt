package model

import com.google.gson.annotations.SerializedName

class IGTimelineMedia(
    @SerializedName("count")
    val count: Int,
    @SerializedName("page_info")
    val pageInfo: IGPageInfo,
    @SerializedName("edges")
    val imagePosts: List<IGImagePost>
)


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
)

class IGFigure(
    @SerializedName("count")
    val count: Int
)