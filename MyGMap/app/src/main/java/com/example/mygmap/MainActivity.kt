package com.example.mygmap

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.mygmap.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var googleMap: GoogleMap
    var loc = LatLng(37.554752, 126.970631)
    val arrLoc = ArrayList<LatLng>()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var startupdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMap()
        initSpinner()
        initLocation()
    }
    private fun initLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(location: LocationResult) {
                if(location.locations.size==0) return
                loc = LatLng(location.locations[location.locations.size-1].latitude,
                location.locations[location.locations.size-1].longitude)
                setCurrentLocation(loc)
                Log.i("location","LocationCallBack()")

            }
        }
    }
    fun setCurrentLocation(location:LatLng){
        val option = MarkerOptions()
        option.position(loc)
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap.addMarker(option)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16.0f))
    }

    fun getLastLocation(){
        if(ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                ),100
            )
        }else{
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it!=null){
                    loc = LatLng(it.latitude,it.longitude)
                    setCurrentLocation(loc)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==100){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startLocationUpdates()
            }else{
                Toast.makeText(this,"위치정보를 제공해야합니다.",Toast.LENGTH_SHORT).show()
                setCurrentLocation(loc)
            }
        }
    }

    fun checkLocationServicesStatus(): Boolean{
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    fun showLocationServicesSetting(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"+
                    "위치 설정을 허용하겠습니까?"
        )
        builder.setPositiveButton("설정",DialogInterface.OnClickListener { dialog, id ->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(GpsSettingIntent,1000)
        })
        builder.setPositiveButton("취소",
        DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            setCurrentLocation(loc)
        })
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1000->{
                if (checkLocationServicesStatus()){
                    Toast.makeText(this,"GPS 활성화 되었음", Toast.LENGTH_SHORT).show()
                    startLocationUpdates()
                }
            }
        }
    }

    fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        )!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                ),100
            )
        }else{
            if (!checkLocationServicesStatus()){
                showLocationServicesSetting()
            }else{
                startupdate = true
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,locationCallback, Looper.getMainLooper()
                )
                Log.i("location", "startLocationUpdates()")
            }

        }
    }

    private fun stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate=false
        Log.i("location", "stopLocationUpdate()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("location","onResume()")
        if(!startupdate)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.i("location", "onPause()")
        stopLocationUpdate()
    }

    private fun initSpinner(){
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
        ArrayList<String>())
        adapter.add("Hybrid")
        adapter.add("Normal")
        adapter.add("Satellite")
        adapter.add("Terrain")
        binding.apply {
            spinner.adapter = adapter
            spinner.setSelection(1)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when(position){
                        0->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                        }
                        1->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

                        }
                        2->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

                        }
                        3->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }

    }

    private fun initMap(){
        initLocation()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
//            googleMap.setMinZoomPreference(10.0f)
//            googleMap.setMaxZoomPreference(18.0f)
//            val option = MarkerOptions()
//            option.position(loc)
//            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//            option.title("역")
//            option.snippet("서울역")
//            val mk1 = googleMap.addMarker(option)
//            mk1.showInfoWindow()
            initMapListener()
        }
    }
    private fun initMapListener(){
        googleMap.setOnMapClickListener {
            arrLoc.add(it)
            googleMap.clear()
            val option = MarkerOptions()
            option.position(it)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            googleMap.addMarker(option)
//            val option2 = PolylineOptions().color(Color.GREEN).addAll(arrLoc)
//            googleMap.addPolyline(option2)
            val option2 = PolygonOptions().fillColor(Color.argb(100, 255, 255, 0)).
            strokeColor(Color.GREEN).addAll(arrLoc)
            googleMap.addPolygon(option2)

        }
    }


}