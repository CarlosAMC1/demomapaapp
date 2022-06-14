package firebase.app.demomapaapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import firebase.app.demomapaapp.databinding.ActivityMapsBinding
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var circle:Circle? = null

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.getUiSettings().setZoomControlsEnabled(true)
        val upt = LatLng(-18.0, -70.2)
        mMap.addMarker(MarkerOptions().position(upt).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(upt))
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val camaraPosition: CameraPosition = CameraPosition.builder()
            .target(upt)
            .zoom(17f)
            .bearing(30f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camaraPosition))
        setMapClick(mMap)
        setMapLongClick(mMap)

    }



    private fun setMapClick(googleMap: GoogleMap){
        googleMap.setOnMapClickListener { latlong->
            googleMap.addMarker(MarkerOptions()
                .position(latlong)
                .icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_YELLOW
                ))
            )
            Toast.makeText(this,"Lat: " + latlong.latitude+ " , " +
            "Long: " + latlong.longitude,Toast.LENGTH_SHORT).show()
            circle?.remove()
            circle = googleMap.addCircle(
                CircleOptions()
                    .center(latlong)
                    .radius(80.0)
                    .fillColor(ContextCompat.getColor(this,R.color.teal_200))
                    .strokeColor(ContextCompat.getColor(this,R.color.purple_700))
            )
        }
    }
    private fun setMapLongClick(googleMap: GoogleMap){
        googleMap.setOnMapLongClickListener { latlong ->
            val snippet:String = String.format(Locale.getDefault(),
            "Lat: %1$.5f, Long: %2$.5f", latlong.latitude,latlong.longitude)
            googleMap.addMarker(MarkerOptions()
                .position(latlong)
                .title(getString(R.string.title_snippet))
                .snippet(snippet)
            )
        }
    }
}