package app.android.mandex.listener

import android.view.View
import app.android.mandex.model.ApiResponse

interface OnMovieClickListener {
    fun onClickOnMovie(view: View?,apiResponse: ApiResponse, position:Int)
}