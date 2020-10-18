package com.practice.movieinfo.remote

import com.practice.movieinfo.model.GetMovieDetailsResponse
import com.practice.movieinfo.model.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "c6b227a1290b114e64b9a89c9f00b84d",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "c6b227a1290b114e64b9a89c9f00b84d",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "c6b227a1290b114e64b9a89c9f00b84d",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("search/movie")
    fun getSearchedMovies(
        @Query("api_key") apiKey: String = "c6b227a1290b114e64b9a89c9f00b84d",
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDerails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = "c6b227a1290b114e64b9a89c9f00b84d"
    ): Call<GetMovieDetailsResponse>
}