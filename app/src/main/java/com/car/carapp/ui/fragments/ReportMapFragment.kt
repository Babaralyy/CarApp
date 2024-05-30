package com.car.carapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.car.carapp.callback.ReportCallback
import com.car.carapp.databinding.FragmentReportMapBinding
import com.car.carapp.utils.Constants.TAG
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReportMapFragment : Fragment(), OnMapReadyCallback {


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
                delay(2000) // Adjust the delay as per your requirement
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

    override fun onMapReady(googleMap: GoogleMap) {

    }
}