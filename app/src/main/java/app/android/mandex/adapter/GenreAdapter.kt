package app.android.mandex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import app.android.mandex.R

class GenreAdapter(private val context: Context, private val genreList: MutableList<String>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genreList[position]
        holder.bindItems(genre)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvGenre : AppCompatTextView?= null

        fun bindItems(genre:String){
            tvGenre = itemView.findViewById(R.id.tvGenre)

            tvGenre?.text = genre
        }

    }


}