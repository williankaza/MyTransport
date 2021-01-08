package br.com.fiap.mytransport.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    /*
    private var INSTANCE: BusService? = null

    val instance: BusService?
        get(){
            if( INSTANCE == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://my-transport-fiap-wyk.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                INSTANCE = retrofit.create(BusService::class.java)
            }

            return INSTANCE
        }
    */
    private var INSTANCE: BusService? = null

    val instance: BusService?
        get() {
            if (INSTANCE == null) {

                val retrofit = Retrofit.Builder()
                        .baseUrl("https://my-transport-fiap-wyk.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                INSTANCE = retrofit.create(BusService::class.java)
            }

            return INSTANCE
        }
}