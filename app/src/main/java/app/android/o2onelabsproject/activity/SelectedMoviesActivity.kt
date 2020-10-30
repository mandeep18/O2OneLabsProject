package app.android.o2onelabsproject.activity

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.android.o2onelabsproject.R
import app.android.o2onelabsproject.activity.MainActivity.Companion.spacingInPixels
import app.android.o2onelabsproject.adapter.SelectedMoviesAdapter
import app.android.o2onelabsproject.base.BaseActivity
import app.android.o2onelabsproject.model.ApiResponse
import app.android.o2onelabsproject.utils.GridSpacingItemDecoration

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