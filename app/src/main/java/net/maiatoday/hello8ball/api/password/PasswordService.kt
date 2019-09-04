package net.maiatoday.hello8ball.api.password

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import net.maiatoday.hello8ball.BuildConfig
import net.maiatoday.hello8ball.api.synonym.SynonymResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PasswordService {
    @GET("query")
    fun getPasswordAsync(
        @Query("command") command: String = "password",
        @Query("format") format: String = "json",
        @Query("count") count: Int = 1
    ): Deferred<PasswordResponse>

    companion object {
        private lateinit var retrofit: Retrofit
        private var baseUrl = "https://www.passwordrandom.com/"
        val instance: PasswordService by lazy {
            passwordService(baseUrl)
        }

        /**
         * static method to initialise the http client and retrofit class
         */
        fun passwordService(url: String): PasswordService {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PasswordService::class.java)
        }
    }


}