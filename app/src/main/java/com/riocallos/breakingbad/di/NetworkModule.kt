package com.riocallos.breakingbad.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.riocallos.breakingbad.BuildConfig
import com.riocallos.breakingbad.services.CharactersService
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Network module for containing web service instances and api helpers
 */
val networkModule = module {

    single { initOkHttpClient() }
    single {
        val gson = GsonBuilder().setPrettyPrinting().create()
        Retrofit
            .Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    single { get<Retrofit>().create(CharactersService::class.java) }

}

fun initOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
        )
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
            chain.proceed(request.build())
        })
        .addInterceptor(Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: IOException) {
                Log.e("okhttp", "IOException")
                e.printStackTrace()
                generateErrorResponse(chain)
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                generateErrorResponse(chain)
            }
        })
    return okHttpClientBuilder.build()
}

fun generateErrorResponse(chain: Interceptor.Chain): Response {
    Log.e("okhttp", "generateErrorResponse")
    val message = "Oops, something went wrong. Please try again later."
    return Response.Builder()
        .code(500)
        .body("{\"error\":\"{\"detail\":\"$message\"}\"}".toResponseBody("application/json".toMediaType()))
        .message(message).protocol(Protocol.HTTP_2)
        .request(chain.request()).build()
}
