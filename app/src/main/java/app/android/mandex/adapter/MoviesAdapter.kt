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
import app.android.mandex.activity.MainActivity
import app.android.mandex.listener.OnMovieClickListener
import app.android.mandex.model.ApiResponse
import com.bumptech.glide.Glide


class MoviesAdapter(private val context: Context, private val moviesList: MutableList<ApiResponse>) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>(){

    private var onMovieClickListener: OnMovieClickListener?=null
    private var mainActivity: MainActivity?=null
    private var showCheckBox = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            parent,
            false
        )
        return MoviesHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = moviesList[position]
        holder.bindItems(movie)
        holder.checkBox?.isChecked = showCheckBox
        holder.checkBox?.visibility = if (showCheckBox) View.VISIBLE else View.GONE
        holder.checkBox?.isChecked = mainActivity?.selectedMoviesList!!.contains(movie)
        Glide.with(context).load(movie.image).into(holder.ivMovie!!)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.rvGenre?.layoutManager = linearLayoutManager
        holder.rvGenre?.adapter = GenreAdapter(context,movie.genre!!)
        holder.rvGenre?.itemAnimator = null

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    internal fun initAdapter(mainActivity: MainActivity, onMovieClickListener: OnMovieClickListener){
        this.onMovieClickListener = onMovieClickListener
        this.mainActivity = mainActivity
    }

    inner class MoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox? = null
        var ivMovie: AppCompatImageView? = null
        var rvGenre: RecyclerView? = null


        fun bindItems(apiResponse: ApiResponse) {
            checkBox = itemView.findViewById(R.id.checkBox)
            rvGenre  = itemView.findViewById(R.id.rvGenre)
            ivMovie  = itemView.findViewById(R.id.ivMovie)

            val textViewName = itemView.findViewById(R.id.tvTitle) as TextView
            val textViewAddress  = itemView.findViewById(R.id.tvRating) as TextView
            textViewName.text = apiResponse.title
            textViewAddress.text = apiResponse.rating.toString()

            checkBox?.setOnClickListener {
                onMovieClickListener?.onClickOnMovie(checkBox, apiResponse, adapterPosition)
            }

            itemView.setOnLongClickListener {
                val selectedItem: ApiResponse = moviesList[adapterPosition]
                if (mainActivity?.selectedMoviesList!=null && !mainActivity?.selectedMoviesList!!.contains(
                        selectedItem
                    )) {
                    mainActivity?.selectedMoviesList?.add(selectedItem)
                }
                notifyItemChanged(adapterPosition)
                mainActivity?.showHideSelectedLayout(true)
                return@setOnLongClickListener false
            }
        }
    }

    fun showAllCheckBox() {
        showCheckBox = true
        notifyDataSetChanged()
    }

    fun hideAllCheckBox() {
        showCheckBox = false
        notifyDataSetChanged()
    }



}