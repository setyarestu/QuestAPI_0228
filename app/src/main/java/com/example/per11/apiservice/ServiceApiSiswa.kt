package com.example.per11.apiservice

import com.example.per11.modeldata.DataSiswa
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(@Body dataSiswa: DataSiswa): retrofit2.Response<Void>

    @GET("bacaTemanById.php")
    suspend fun getSatuSiswa(@Query("id") id: Int): DataSiswa

    @POST("updateTM.php")
    suspend fun editSatuSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa): retrofit2.Response<Void>

    @GET("deleteTM.php")
    suspend fun hapusSatuSiswa(@Query("id") id: Int): retrofit2.Response<Void>
}