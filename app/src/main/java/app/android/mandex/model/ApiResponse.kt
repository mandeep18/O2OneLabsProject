package app.android.mandex.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("rating")
    var rating: Double = 0.0,

    @SerializedName("genre")
    var genre: MutableList<String>?=null
):Serializable