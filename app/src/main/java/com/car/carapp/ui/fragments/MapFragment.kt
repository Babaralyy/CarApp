package com.car.carapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.car.carapp.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var mBinding: FragmentMapBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMapBinding.inflate(inflater)

        inIt(savedInstanceState)

        return mBinding.root
    }

    private fun inIt(savedInstanceState: Bundle?) {

        mBinding.progressCircular.visibility = View.VISIBLE

        // Start the coroutine
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                // Simulate some background work
                delay(2000) // Adjust the delay as per your requirement
            }

            // Continue with UI work on the main thread
            mBinding.mapView.onCreate(savedInstanceState)
            mBinding.mapView.getMapAsync(this@MapFragment)

            mBinding.progressCircular.visibility = View.GONE

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
/*        val latitude = 37.4220
        val longitude = -122.0841
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in Googleplex"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))*/
    }
}