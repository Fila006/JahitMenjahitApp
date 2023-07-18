package com.example.jahitmenjahit.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jahitmenjahit.application.SewApplication
import com.example.jahitmenjahit.databinding.FragmentSecondBinding
import com.example.jahitmenjahit.model.Sew
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.R
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.log

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val sewViewModel: SewViewModel by viewModels {
        SewViewModelFactory((applicationContext as SewApplication).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var sew : Sew? = null
    private lateinit var mMap: GoogleMap
    private var currentLetLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,
            savedInstanceState)

        sew = args.sew
        //jika kita cek sew null maka tampilan default tambah jahitan
        //jika sew tidak null tampilan sedikit berubah ada tombol hapus dan jahit
        if (sew != null){
            binding.Deletebutton.visibility = View.VISIBLE
            binding.Savebutton.text = "Ubah"
            binding.NameFragment.setText(sew?.name)
            binding.AddressFragment.setText(sew?.address)
            binding.ManyOrdesFragment.setText(sew?.manyorders)
            binding.SizeFragment.setText(sew?.size)
        }

        //binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(com.example.jahitmenjahit.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()


        val name = binding.NameFragment.text
        val address = binding.AddressFragment.text
        val manyordes = binding.ManyOrdesFragment.text
        val size = binding.SizeFragment.text
        binding.Savebutton.setOnClickListener {
            //kita beri kondisi dimana jika tambah jahitan kosong tidak bisa menyimpan
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()){
                    Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (manyordes.isEmpty()){
                Toast.makeText(context, "Banyak pesanan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (size.isEmpty()){
                Toast.makeText(context, "Ukuran tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else {
                //kasih defaul untuk test berhasil atau tidak
                if (sew == null){
                    val sew = Sew(0, name.toString(), address.toString(), manyordes.toString(), size.toString(), currentLetLang?.latitude, currentLetLang?.longitude)
                    sewViewModel.insert(sew)
                } else {
                    val sew = Sew(sew?.id!!, name.toString(), address.toString(), manyordes.toString(), size.toString(),  currentLetLang?.latitude, currentLetLang?.longitude)
                    sewViewModel.update(sew)
                }
                findNavController().popBackStack() //untuk dismiss halaman ini
            }
        }
        binding.Deletebutton.setOnClickListener {
            sew?.let {  sewViewModel.delete(it) }
            findNavController().popBackStack() //untuk dismiss halaman ini
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //implement drag map

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLetLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLetLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation(){
        // mengecek jika permission tidak disetujui maka akan berhenti dikondisi if
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        //untuk test curret location coba dirun di device langsung atau di build apknya trus di install di hpnya masing-masing
        fusedLocationClient.lastLocation
            .addOnSuccessListener {location ->
                if (location != null){
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLetLang = latLang
                    var title = "JAHIT"
                    //menampilkan lokasi sesuai koordinat yang sudah disimpan atau diupdate

                    if (sew != null){
                        title = sew?.name.toString()
                        val newCurrentLocation = LatLng(sew?.latitude!!, sew?.longitude!!)
                        latLang = newCurrentLocation
                    }
                    val makerOption = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(com.example.jahitmenjahit.R.drawable.ic_sewing_32))
                    mMap.addMarker(makerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 15f))
                }
            }
    }
}