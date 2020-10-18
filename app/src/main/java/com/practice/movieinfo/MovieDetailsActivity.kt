package com.practice.movieinfo

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.practice.movieinfo.data.MoviesRepository
import com.practice.movieinfo.model.GetMovieDetailsResponse

const val MOVIE_ID = "extra_movie_id"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var budget: TextView
    private lateinit var revenue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        budget = findViewById(R.id.movie_budget)
        revenue = findViewById(R.id.movie_revenue)

        val extras = intent.extras

        if (extras != null) {
            val movieId = extras.getLong(MOVIE_ID)
            getMovieDetails(movieId)
        } else {
            finish()
        }
    }

    private fun getMovieDetails(movieId: Long) {
        MoviesRepository.getMovieDetails(
            movieId,
            ::onMovieDetailsFetched,
            ::onError
        )
    }


    private fun onMovieDetailsFetched(getMovieDetailsResponse: GetMovieDetailsResponse) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280${getMovieDetailsResponse.backdropPath}")
            .transform(CenterCrop())
            .into(backdrop)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342${getMovieDetailsResponse.posterPath}")
            .transform(CenterCrop())
            .into(poster)

        title.text = getMovieDetailsResponse.title
        rating.rating = getMovieDetailsResponse.rating
        releaseDate.text = getMovieDetailsResponse.releaseDate
        overview.text = getMovieDetailsResponse.overview

        val budgetText =getString(R.string.budget) + amountOfMoneyFormatting(getMovieDetailsResponse.budget)
        val revenueText =getString(R.string.revenue) + amountOfMoneyFormatting(getMovieDetailsResponse.revenue)

        budget.text = budgetText
        revenue.text = revenueText
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun amountOfMoneyFormatting(amountOfMoney: Long):String{
        return when{
            amountOfMoney > 1000000  -> "$${String.format("%.2f", amountOfMoney/1000000.0)} million"
            amountOfMoney > 1000  -> "$${String.format("%.2f", amountOfMoney/1000.0)} thousand"
            else -> "$${amountOfMoney}"
        }
    }

}