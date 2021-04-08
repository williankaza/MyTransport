package br.com.fiap.mytransport.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ScheduleAPIService {
    private var INSTANCE: ScheduleService? = null

    val instance: ScheduleService?
        get() {
            if (INSTANCE == null) {

                val retrofit = Retrofit.Builder()
                        .baseUrl("https://mytransport-msusuario-fiap.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                INSTANCE = retrofit.create(ScheduleService::class.java)
            }

            return INSTANCE
        }
}