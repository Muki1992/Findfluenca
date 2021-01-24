package service

import com.google.gson.Gson
import com.google.gson.JsonDeserializer
import model.IGUser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import model.IGImagePost
import model.IGTimelineMedia


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



            val imageDeserializer = JsonDeserializer { json, typeOfT, context ->
                val mediaPostContent = json?.asJsonObject?.get("node")
                Gson().fromJson(mediaPostContent!!, IGImagePost::class.java)
            }

            val graphQLDeserializer = JsonDeserializer { json, typeOfT, context ->
                val userContent = json?.asJsonObject?.get("graphql")?.asJsonObject?.get("user")
                val gBuilderTemp = GsonBuilder()

                gBuilderTemp.registerTypeAdapter(IGImagePost::class.java, imageDeserializer)
                gBuilderTemp.create().fromJson(userContent!!, IGUser::class.java)
            }

            val gsonBuilder = GsonBuilder()
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


}