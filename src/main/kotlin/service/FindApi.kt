package service

import io.reactivex.Observable
import model.IGUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FindApi {

    @GET("{username}/")
    fun loadProfile(@Path("username") username: String,
                    @Query("__a") number: Int = 1): Observable<IGUser>
}