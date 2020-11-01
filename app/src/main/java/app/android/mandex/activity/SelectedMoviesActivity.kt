package app.android.mandex.activity

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.android.mandex.R
import app.android.mandex.activity.MainActivity.Companion.spacingInPixels
import app.android.mandex.adapter.SelectedMoviesAdapter
import app.android.mandex.base.BaseActivity
import app.android.mandex.model.ApiResponse
import app.android.mandex.utils.GridSpacingItemDecoration

class SelectedMoviesActivity : BaseActivity() {
    private var adapter = SelectedMoviesAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_movies)
        getBundle()
    }

    private fun getBundle(){
        val randomWorkout = intent.extras?.getParcelableArrayList<Parcelable>(MainActivity.KEY_SELECTED_MOVIES) as MutableList<ApiResponse>
        setUpViews(randomWorkout)
        Log.d("TAG","sizeOfList-${randomWorkout.size}")

    }

    private fun setUpViews(moviesList: MutableList<ApiResponse>) {
        val rvMovie = findViewById<RecyclerView>(R.id.rvMovie)
        val itemDecorate= GridSpacingItemDecoration(2, spacingInPixels, true, 0)
        rvMovie.layoutManager = GridLayoutManager(this, 2)
        rvMovie.addItemDecoration(itemDecorate)
        adapter = SelectedMoviesAdapter(this, moviesList)
        rvMovie.adapter = adapter
    }
}