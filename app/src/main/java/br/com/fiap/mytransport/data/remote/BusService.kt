package br.com.fiap.mytransport.data.remote

import br.com.fiap.mytransport.model.OnibusPayload
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BusService {

    @GET("/onibus")
    fun pesquisarTodos(): Call<List<OnibusPayload>>

    @GET("/onibus/{numero}")
    fun pesquisar(@Path("numero") numero: String ): Call<OnibusPayload>

    @POST("/onibus/{numero}/favoritar")
    fun atualizarFavorito(@Path("numero") numero: String): Call<OnibusPayload>

}