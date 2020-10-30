package app.android.o2onelabsproject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.android.o2onelabsproject.R
import app.android.o2onelabsproject.adapter.MoviesAdapter
import app.android.o2onelabsproject.base.BaseActivity
import app.android.o2onelabsproject.listener.OnMovieClickListener
import app.android.o2onelabsproject.model.ApiResponse
import app.android.o2onelabsproject.network.ApiService
import app.android.o2onelabsproject.utils.GridSpacingItemDecoration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : BaseActivity() {
    private var fBtnDone:FloatingActionButton?=null
    private var progressbar:ProgressBar?=null
    private var adapter = MoviesAdapter(this, arrayListOf())
    var selectedMoviesList: MutableList<ApiResponse> = arrayListOf()
    var moviesListFromServer : MutableList<ApiResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()
        getDataFromServer()
    }

    private fun initViews(){
        fBtnDone = findViewById(R.id.fBtnDone)
        progressbar = findViewById(R.id.progressbar)
    }

    private fun setListeners(){
        fBtnDone?.setOnClickListener(onClickListener)
    }

    private fun setUpViews(moviesList: MutableList<ApiResponse>) {
        val rvMovie = findViewById<RecyclerView>(R.id.rvMovie)
        val itemDecorate= GridSpacingItemDecoration(2, spacingInPixels, true, 0)

        rvMovie.layoutManager = GridLayoutManager(this, 2)
        rvMovie.addItemDecoration(itemDecorate)
        adapter = MoviesAdapter(this, moviesList)
        rvMovie.adapter = adapter

        adapter.initAdapter(this,onMovieClickListener)
    }


    private fun getDataFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.callMoviesListFromServer()
        call.enqueue(object : Callback<MutableList<ApiResponse>> {
            override fun onResponse(call: Call<MutableList<ApiResponse>>, response: Response<MutableList<ApiResponse>>) {
                if (response.code() == 200) {
                    moviesListFromServer = response.body()!!
                    Log.d("TAG", "response-" + response.body())
                    setUpViews(moviesListFromServer)
                    progressbar?.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<ApiResponse>>, t: Throwable) {
                Log.e("TAG", "error-" + t.message)
                showToast(t.message)
                progressbar?.visibility = View.GONE

            }
        })
    }
    private val onClickListener:View.OnClickListener = View.OnClickListener {
        when(it.id){
            R.id.fBtnDone -> {
                if(selectedMoviesList.size>0){
                    startActivity(Intent(this, SelectedMoviesActivity::class.java).putExtra(
                        KEY_SELECTED_MOVIES,ArrayList(selectedMoviesList)))
                }else{
                    showToast("Select at least one")
                }
            }
        }
    }

    private val onMovieClickListener: OnMovieClickListener = object  : OnMovieClickListener {
        override fun onClickOnMovie(view: View?,apiResponse: ApiResponse, position:Int) {
            prepareSelection(view,position)
        }
    }

    fun prepareSelection(view: View?, position: Int) {
        try {
            if ((view as CheckBox).isChecked) {
                selectedMoviesList.add(moviesListFromServer[position])
            } else {
                selectedMoviesList.remove(moviesListFromServer[position])
            }
        } catch (e: Exception) {
            Log.d("TAG", "ONCHECKED- " + e.message)
        }
    }


    fun showHideSelectedLayout(boolean: Boolean){
        if(boolean){
            fBtnDone?.visibility = View.VISIBLE
            adapter.showAllCheckBox()
            adapter.notifyDataSetChanged()
        }else{
            fBtnDone?.visibility = View.GONE
            adapter.hideAllCheckBox()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBackPressed() {
        if(selectedMoviesList.size>0){
            showHideSelectedLayout(false)
            selectedMoviesList.clear()
        }else{
            super.onBackPressed()
        }
    }

    companion object{
        const val BaseUrl = "http://api.androidhive.info/"
        const val KEY_SELECTED_MOVIES= "selected_movies"
        const val spacingInPixels: Int = 30

    }
}