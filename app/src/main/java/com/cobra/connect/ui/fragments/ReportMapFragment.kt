package com.cobra.carapp.ui.fragments

import android.content.Context
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
import com.cobra.carapp.BuildConfig
import com.cobra.carapp.R
import com.cobra.carapp.databinding.FragmentReportMapBinding
import com.cobra.carapp.datamodels.DirectionsClient
import com.cobra.carapp.network.ApiCall
import com.cobra.carapp.ui.activities.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class ReportMapFragment : Fragment(), OnMapReadyCallback {

    private var activity: MainActivity? = null
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

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchRoute() {
        mBinding.progressCircular.visibility = View.VISIBLE
        val origin = LatLng(32.0964525, 34.8781551)
        val destination = LatLng(32.0988488, 34.8806121)

        // Use Kotlin coroutines
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val service = DirectionsClient.getClient()?.create(ApiCall::class.java)
                val response = service?.getDirections(
                    "${origin.latitude},${origin.longitude}",
                    "${destination.latitude},${destination.longitude}",
                    false,
                    "driving",
                    BuildConfig.ApiKey
                )?.execute()

                withContext(Dispatchers.Main) {
                    mBinding.progressCircular.visibility = View.GONE
                    if (response?.isSuccessful == true && response.body() != null) {
                        val startMarker =
                            getBitmapDescriptorFromDrawable(R.drawable.ic_marker_start)
                        val endMarker = getBitmapDescriptorFromDrawable(R.drawable.ic_marker_end)

                        // Add custom markers and move the camera
                        mMap.addMarker(
                            MarkerOptions()
                                .position(origin)
                                .title("Marker 1")
                                .icon(startMarker)
                        )

                        mMap.addMarker(
                            MarkerOptions()
                                .position(destination)
                                .title("Marker 2")
                                .icon(endMarker)
                        )

                        if(response.body()?.routes?.isNotEmpty() == true){
                            val routePoints =
                                response.body()?.routes?.get(0)?.overview_polyline?.points?.let {
                                    decodePolyline(
                                        it
                                    )
                                }
                            routePoints?.let { drawPolyline(it) }
                        }
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    mBinding.progressCircular.visibility = View.GONE
                    // Handle network error
                }
            }
        }
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
            val dLat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dLat

            shift = 0
            result = 0
            do {
                b = encodedPolyline[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dLng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dLng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }


    private fun getBitmapDescriptorFromDrawable(drawableId: Int): BitmapDescriptor? {
            val drawable: Drawable =
                activity?.let { ContextCompat.getDrawable(it, drawableId) } ?: return null
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
    }
}
