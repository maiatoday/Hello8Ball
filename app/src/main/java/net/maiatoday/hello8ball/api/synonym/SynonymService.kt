package net.maiatoday.hello8ball.api.synonym

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import net.maiatoday.hello8ball.BuildConfig
import net.maiatoday.hello8ball.api.password.PasswordService
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface SynonymService {

    @Headers(
        "x-rapidapi-host: wordsapiv1.p.rapidapi.com"
    )
    @GET("words/{word}/synonyms")
    fun getSynonymAsync(
        @Path("word") word: String,
        @Header("x-rapidapi-key") apiKey: String = BuildConfig.WORDS_API_KEY
    ): Deferred<SynonymResponse>

    companion object {
        private lateinit var retrofit: Retrofit
        private var baseUrl = "https://wordsapiv1.p.rapidapi.com/"
        val instance: SynonymService by lazy {
            synonymService(HttpUrl.get(baseUrl))
        }

        /**
         * static method to initialise the http client and retrofit class
         */
        fun synonymService(url: HttpUrl): SynonymService {
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

            return retrofit.create(SynonymService::class.java)
        }
    }
}