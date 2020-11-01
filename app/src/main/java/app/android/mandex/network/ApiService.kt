package app.android.mandex.network

import app.android.mandex.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("json/movies.json")
    fun callMoviesListFromServer(): Call<MutableList<ApiResponse>>
}