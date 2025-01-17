package com.polstat.digitalarchive.api
import com.polstat.digitalarchive.models.Archive
import com.polstat.digitalarchive.models.AuthRequest
import com.polstat.digitalarchive.models.AuthResponse
import com.polstat.digitalarchive.models.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body user: User): User

    @GET("archives")
    suspend fun getAllArchives(): List<Archive>

    @GET("archives/{id}")
    suspend fun getArchiveById(@Path("id") id: Long): Archive

    @PUT("archives/{id}")
    suspend fun updateArchive(@Path("id") id: Long, @Body archive: Archive): Archive

    @GET("archives/search")
    suspend fun searchArchives(@Query("keyword") keyword: String): List<Archive>

    @POST("archives/create")
    suspend fun createArchive(@Body archive: Archive): Archive

    @DELETE("archives/{id}")
    suspend fun deleteArchive(@Path("id") id: Long)

    @GET("users/current")
    suspend fun getCurrentUser(@Query("email") email: String): User

    @PUT("users/{userId}/profile")
    suspend fun updateProfile(@Path("userId") userId: Long, @Body user: User): User

    @PUT("users/{userId}/change-password")
    suspend fun changePassword(
        @Path("userId") userId: Long,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): ResponseBody
}