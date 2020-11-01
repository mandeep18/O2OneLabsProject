package app.android.mandex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.android.mandex.R
import app.android.mandex.model.ApiResponse
import com.bumptech.glide.Glide

class SelectedMoviesAdapter (private val context: Context, private val moviesList: MutableList<ApiResponse>) : RecyclerView.Adapter<SelectedMoviesAdapter.MoviesHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = moviesList[position]
        holder.bindItems(movie)
        holder.checkBox?.visibility = View.VISIBLE
        holder.checkBox?.isChecked = true
        holder.checkBox?.isClickable = false
        Glide.with(context).load(movie.image).into(holder.ivMovie!!)


        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.rvGenre?.layoutManager = linearLayoutManager
        holder.rvGenre?.adapter = GenreAdapter(context,movie.genre!!)
        holder.rvGenre?.itemAnimator = null

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    inner class MoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox? = null
        var ivMovie: AppCompatImageView? = null
        var rvGenre: RecyclerView? = null
        fun bindItems(apiResponse: ApiResponse) {
            checkBox = itemView.findViewById(R.id.checkBox)
            rvGenre  = itemView.findViewById(R.id.rvGenre)
            ivMovie  = itemView.findViewById(R.id.ivMovie) as AppCompatImageView

            val textViewName = itemView.findViewById(R.id.tvTitle) as TextView
            val textViewAddress  = itemView.findViewById(R.id.tvRating) as TextView
            textViewName.text = apiResponse.title
            textViewAddress.text = apiResponse.rating.toString()
        }
    }

}