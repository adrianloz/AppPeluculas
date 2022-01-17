package com.adrianloz.myapplication.network

import com.adrianloz.myapplication.models.NowPlaying
import com.adrianloz.myapplication.models.Popular
import com.adrianloz.myapplication.models.Video
import com.adrianloz.myapplication.utils.Keys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(Keys.NOW_PLAYING_URL)
    suspend fun getNowPlayingMovies(): NowPlaying?

    @GET(Keys.POPULAR_MOVIES_URL)
    suspend fun getPopularMovies(): Popular?

    @GET(Keys.VIDEO_URL)
    suspend fun getVideo(@Path("movie_id") id: Long): Video?

    companion object{
        fun getInstance(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().apply {
                addInterceptor(object :Interceptor{
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val originalRequest = chain.request()
                        val originalUrl = originalRequest.url
                        val newRequest=originalRequest.newBuilder().apply {
                            url(originalUrl.newBuilder().addQueryParameter(
                                "api_key",Keys.API_KEY).build())
                        }.build()
                        return chain.proceed(newRequest)
                    }
                })
            }
            httpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(Keys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ApiService::class.java)
        }
    }

}