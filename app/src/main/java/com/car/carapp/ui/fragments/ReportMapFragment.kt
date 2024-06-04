package com.car.carapp.ui.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.car.carapp.R
import com.car.carapp.databinding.FragmentReportMapBinding
import com.car.carapp.datamodels.DirectionsClient
import com.car.carapp.datamodels.DirectionsResponse
import com.car.carapp.network.ApiCall
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReportMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mBinding: FragmentReportMapBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentReportMapBinding.inflate(inflater)

        inIt(savedInstanceState)

        return mBinding.root
    }

    private fun inIt(savedInstanceState: Bundle?) {
        mBinding.progressCircular.visibility = View.VISIBLE

        // Start the coroutine
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                // Simulate some background work
                delay(1000) // Adjust the delay as per your requirement
            }

            // Continue with UI work on the main thread
            mBinding.mapView.onCreate(savedInstanceState)
            mBinding.mapView.getMapAsync(this@ReportMapFragment)

            mBinding.progressCircular.visibility = View.GONE

        }

        mBinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    /*    override fun onMapReady(googleMap: GoogleMap) {
            try {
    //            addMarkerToMap(googleMap)


                // Example coordinates
                val point1 = LatLng(-34.0, 151.0)
                val point2 = LatLng(-35.0, 150.0)
            }catch (e: Exception){
                Log.i(TAG, "onMapReady:: ${e.message}")
            }
        }*/


    private fun fetchRoute() {
        mBinding.progressCircular.visibility = View.VISIBLE
        val origin = LatLng(32.0964525, 34.8781551)
        val destination = LatLng(32.0988488, 34.8806121)

        val service = DirectionsClient.getClient()?.create(ApiCall::class.java)
        val call = service?.getDirections(
            "${origin.latitude},${origin.longitude}",
            "${destination.latitude},${destination.longitude}",
            false,
            "driving",
            "AIzaSyAnxKP2t1MupyAcsRd0nu5wi05ijy-YOJQ"
        )

        call?.enqueue(object : Callback<DirectionsResponse> {
            override fun onResponse(
                call: Call<DirectionsResponse>,
                response: Response<DirectionsResponse>
            ) {
                mBinding.progressCircular.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {

                    val startMarker = getBitmapDescriptorFromDrawable(R.drawable.ic_marker_start)
                    val endMarker = getBitmapDescriptorFromDrawable(R.drawable.ic_marker_end)

                    // Add custom markers and move the camera
                    val marker1 = LatLng(32.0964525, 34.8781551)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(marker1)
                            .title("Marker 1")
                            .icon(startMarker)
                    )


                    val marker2 = LatLng(32.0988488, 34.8806121)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(marker2)
                            .title("Marker 2")
                            .icon(endMarker)
                    )


                    val routePoints =
                        response.body()?.routes?.get(0)?.overviewPolyline?.points?.let {
                            decodePolyline(
                                it
                            )
                        }
                    routePoints?.let { drawPolyline(it) }
                }
            }

            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                mBinding.progressCircular.visibility = View.GONE
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fetchRoute()

        val origin = LatLng(32.0964525, 34.8781551)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15f))
    }

    private fun drawPolyline(routePoints: List<LatLng>) {
        val options = PolylineOptions().apply {
            addAll(routePoints)
            width(5f)
            color(Color.BLACK)
        }
        mMap.addPolyline(options)
    }

    private fun decodePolyline(encodedPolyline: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encodedPolyline.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encodedPolyline[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encodedPolyline[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }

    private fun addMarkerToMap(googleMap: GoogleMap) {
        val startMarker = getBitmapDescriptorFromDrawable(R.drawable.ic_marker_start)
        val endMarker = getBitmapDescriptorFromDrawable(R.drawable.ic_marker_end)

        val points = listOf(
            LatLng(32.0964525, 34.8781551),
            LatLng(32.0968495, 34.8809981),
            LatLng(32.0957765, 34.8816101),
            LatLng(32.0988488, 34.8806121),
        )

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker1, 15f))

        // Draw a line between markers

        points.forEachIndexed { index, point ->
            if (index == 0) {
                // Add custom markers and move the camera
                val marker1 = LatLng(32.0964525, 34.8781551)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(marker1)
                        .title("Marker 1")
                        .icon(startMarker)
                )
            }
            if (index == 3) {
                val marker2 = LatLng(point.latitude, point.longitude)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(marker2)
                        .title("Marker 2")
                        .icon(endMarker)
                )
            }
//            googleMap.addMarker(MarkerOptions().position(point).title("Point $index at ${point.latitude}, ${point.longitude}"))
        }

//        drawRoute(marker1, marker2, googleMap, points)
        drawRoute(googleMap, points)
    }

    private fun drawRoute(googleMap: GoogleMap, points: List<LatLng>) {
//        val polylineOptions = PolylineOptions()
//            .add(start)
//            .add(end)
//            .width(5f)
//            .color(resources.getColor(R.color.black, null))

        val polylineOptions = PolylineOptions()
            .addAll(points)
            .width(5f)
            .color(resources.getColor(R.color.black, null))

        googleMap.addPolyline(polylineOptions)


        // Move the camera to the first point in the polyline
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points[0], 15f))
    }

    private fun getBitmapDescriptorFromDrawable(drawableId: Int): BitmapDescriptor? {
        val drawable: Drawable =
            ContextCompat.getDrawable(requireContext(), drawableId) ?: return null
        val canvas = Canvas()
        val bitmap: Bitmap = Bitmap.createBitmap(
            50,
            50,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
