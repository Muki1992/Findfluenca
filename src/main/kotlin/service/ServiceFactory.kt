package service

import com.google.gson.Gson
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import model.*
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.SocketAddress
import java.util.concurrent.TimeUnit


class ServiceFactory {

    companion object {

        fun getService(): FindApi {
            return buildRetrofit().create(FindApi::class.java)
        }

        private fun buildRetrofit(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            httpClient.addInterceptor(interceptor)
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
//            httpClient.proxy( Proxy(Proxy.Type.HTTP, InetSocketAddress("182.253.172.222", 8080)))


            val imageTextsDeserializer = JsonDeserializer { json, typeOfT, context ->
                val mediaPostContent = json?.asJsonObject?.get("node")
                Gson().fromJson(mediaPostContent!!, IGImageText::class.java)
            }

            val imageDeserializer = JsonDeserializer { json, typeOfT, context ->
                val mediaPostContent = json?.asJsonObject?.get("node")
                val gBuilderTemp = GsonBuilder()
                gBuilderTemp.registerTypeAdapter(IGImageText::class.java, imageTextsDeserializer)
                gBuilderTemp.create().fromJson(mediaPostContent!!, IGImagePost::class.java)
            }



            val graphQLDeserializer = JsonDeserializer { json, typeOfT, context ->
                val userContent = json?.asJsonObject?.get("graphql")?.asJsonObject?.get("user")
                val gBuilderTemp = GsonBuilder()

                gBuilderTemp.registerTypeAdapter(IGImagePost::class.java, imageDeserializer)
                gBuilderTemp.create().fromJson(userContent!!, IGUser::class.java)
            }



            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(IGImageText::class.java, imageTextsDeserializer)
            gsonBuilder.registerTypeAdapter(IGUser::class.java, graphQLDeserializer)
            gsonBuilder.registerTypeAdapter(IGImagePost::class.java, imageDeserializer)

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.instagram.com/")
                .client(httpClient.build())
                .build()
        }


    }

// 2003:e7:f74f:af01:f532:f6b7:d0ad:5ba1

// 2003:e7:f74f:d601:8d59:a070:a6b8:7dc
}