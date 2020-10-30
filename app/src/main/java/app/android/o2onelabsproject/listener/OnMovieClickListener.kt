package app.android.o2onelabsproject.listener

import android.view.View
import app.android.o2onelabsproject.model.ApiResponse

interface OnMovieClickListener {
    fun onClickOnMovie(view: View?,apiResponse: ApiResponse, position:Int)
}