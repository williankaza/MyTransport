package br.com.fiap.mytransport.ui.maps

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.mytransport.R
import br.com.fiap.mytransport.data.remote.APIService
import br.com.fiap.mytransport.data.repository.BusRepositoryImpl
import br.com.fiap.mytransport.domain.repository.BusRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var busRepository: BusRepository
    private lateinit var onibusSelecionado: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onibusSelecionado = intent.getStringExtra("NUMERO_ONIBUS").toString()
        busRepository = BusRepositoryImpl(APIService.instance)

        btReturn.setOnClickListener{
            goPreviousActivity()
        }
    }

    private fun goPreviousActivity() {
        super.finish()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
/*
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
*/
        busRepository.pesquisarUmaLinha(
            onibusSelecionado,
            { onibus ->
                if (onibus != null) {
                    var newLatLng = LatLng(0.0,0.0)
                    for (ponto in onibus.lsPontos) {
                        if (newLatLng.latitude == 0.0 && newLatLng.longitude == 0.0){
                            newLatLng = LatLng(ponto.latitude.toDouble(), ponto.longitude.toDouble())
                            mMap.addMarker(MarkerOptions().position(newLatLng).title(onibus.numero + " - Inicio").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                        }


                        val novoMarker = LatLng(ponto.latitude.toDouble(), ponto.longitude.toDouble())
                        mMap.addPolyline(PolylineOptions()
                                .add(newLatLng, novoMarker)
                                .width(5f)
                                .color(Color.RED))

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(novoMarker))
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15F), 2000, null)
                        newLatLng = novoMarker
                    }
                    mMap.addMarker(MarkerOptions().position(newLatLng).title(onibus.numero + " - Fim ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                }

            },
            {

            }
        )
    }

}