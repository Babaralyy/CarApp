package com.cobra.connect.ui.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.cobra.connect.R
import com.cobra.connect.databinding.FragmentNotificationDetailBinding
import com.cobra.connect.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationDetailFragment : Fragment(), OnMapReadyCallback {


    private lateinit var mBinding: FragmentNotificationDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNotificationDetailBinding.inflate(inflater)

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
            mBinding.mapView.getMapAsync(this@NotificationDetailFragment)

            mBinding.progressCircular.visibility = View.GONE

        }

        mBinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latitude = 32.09898
        val longitude = 34.8708675
        val location = LatLng(latitude, longitude)

        try {
            val bitmapDescriptor = getBitmapDescriptorFromDrawable(R.drawable.ic_marker)

            // Add a marker with the custom drawable
            googleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Marker in Googleplex")
                    .icon(bitmapDescriptor)
            )

            // Move the camera to the marker
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

        } catch (e: Exception) {
            Log.i(Constants.TAG, "onMapReady:: ${e.message}")
        }

    }

    private fun getBitmapDescriptorFromDrawable(drawableId: Int): BitmapDescriptor? {
        val drawable: Drawable =
            ContextCompat.getDrawable(requireContext(), drawableId) ?: return null
        val canvas = Canvas()
        val bitmap: Bitmap = Bitmap.createBitmap(
            60,
            60,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}