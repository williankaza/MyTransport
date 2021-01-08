package br.com.fiap.mytransport.data.repository

import android.util.Log
import android.util.Log.INFO
import br.com.fiap.mytransport.data.remote.BusService
import br.com.fiap.mytransport.data.remote.mapper.BusPayloadMapper
import br.com.fiap.mytransport.domain.entity.Onibus
import br.com.fiap.mytransport.domain.repository.BusRepository
import br.com.fiap.mytransport.model.OnibusPayload
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class BusRepositoryImpl (
    val busService: BusService?
): BusRepository {
    override fun pesquisarTudo(onComplete: (List<Onibus>?) -> Unit, onError: (Throwable) -> Unit) {
        busService
            ?.pesquisarTodos()
            ?.enqueue(object: Callback<List<OnibusPayload>>{
                override fun onResponse(
                    call: Call<List<OnibusPayload>>,
                    response: Response<List<OnibusPayload>>
                ) {
                    if (response.isSuccessful){
                        val onibusPayload = response.body()
                        Log.d("wyk","wyk - Entrou no Payload")
                        if (onibusPayload == null){
                            onError(Throwable("Não foram encontradas linhas!"))
                        } else {
                            onComplete(BusPayloadMapper.map(onibusPayload))
                        }
                    } else {
                        onError(Throwable("Não foram encontradas linhas!"))
                    }
                }

                override fun onFailure(call: Call<List<OnibusPayload>>, t: Throwable) {
                    onError(t)
                }

            })
    }

    override fun pesquisarUmaLinha(
        id: String,
        onComplete: (Onibus?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        busService
            ?.pesquisar(id)
            ?.enqueue(object: Callback<OnibusPayload>{
                override fun onResponse(
                    call: Call<OnibusPayload>,
                    response: Response<OnibusPayload>
                ) {
                    if (response.isSuccessful){
                        val onibusPayload = response.body()

                        if (onibusPayload == null){
                            onError(Throwable("Onibus não encontrado!"))
                        } else {
                            onComplete(BusPayloadMapper.map(onibusPayload))
                        }
                    } else {
                        onError(Throwable("Onibus não encontrado!"))
                    }
                }

                override fun onFailure(call: Call<OnibusPayload>, t: Throwable) {
                    onError(t)
                }

            })
    }


}