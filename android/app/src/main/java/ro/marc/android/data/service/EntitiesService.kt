package ro.marc.android.data.service

import retrofit2.Call
import retrofit2.http.*
import ro.marc.android.data.api.BusinessPayload
import ro.marc.android.data.api.dto.EntityDTO

interface EntitiesService {

    @GET("/")
    fun fetch(): Call<BusinessPayload<List<EntityDTO>>>

    @POST("/")
    fun post(@Body dto: EntityDTO): Call<BusinessPayload<EntityDTO>>

    @PATCH("/{id}")
    fun patch(@Body dto: EntityDTO, @Path("id") id: Long): Call<BusinessPayload<EntityDTO>>

}