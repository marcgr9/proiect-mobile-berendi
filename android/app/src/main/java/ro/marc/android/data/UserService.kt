package ro.marc.android.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ro.marc.android.data.api.BusinessPayload
import ro.marc.android.data.api.dto.LoginCredentials
import ro.marc.android.data.api.dto.TokenDTO

interface UserService {

    @GET("/me")
    fun me(): Call<BusinessPayload<Void>>

    @POST("/login")
    fun login(@Body credentials: LoginCredentials): Call<BusinessPayload<TokenDTO>>

}