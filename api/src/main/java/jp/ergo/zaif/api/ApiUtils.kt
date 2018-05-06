package jp.ergo.zaif.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import jp.ergo.zaif.api.entity.Depth
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiUtils {
    private val baseUrl = "https://api.zaif.jp"
    fun <T> createApiCaller(clazz: Class<T>, baseUrl: String? = null): T {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        httpClient.followRedirects(true)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)


        val typeAdapters = mapOf(
                Depth.Element.adapter
        )

        val retrofit = Retrofit.Builder().baseUrl(baseUrl ?: this.baseUrl)
                .addConverterFactory(GsonConverterFactory
                        .create(GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                .let {
                                    typeAdapters.forEach { e ->
                                        it.registerTypeAdapter(e.key, e.value)
                                    }
                                    it
                                }.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()

        return retrofit.create(clazz)
    }
}