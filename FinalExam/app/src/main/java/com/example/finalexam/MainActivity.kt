package com.example.finalexam

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalexam.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var googleMap: GoogleMap
    lateinit var myDBHelper: DjhDBHelper

    var loc = LatLng(37.554752, 126.970631)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDB()
        initMap()
        init()
    }

    fun clearEditText(){
        binding.apply {
            editPlace.text.clear()
        }
    }

    private fun init() {
        myDBHelper = DjhDBHelper(this)
        binding.apply {

//            insertbtn.setOnClickListener {
//                val name = pNameEdit.text.toString()
//                val quantity = pQuantityEdit.text.toString().toInt()
//                val product = Product(0, name, quantity)
//                val result = myDBHelper.insertProduct(product)
//                if(result){
//                    getAllRecord()
//                    Toast.makeText(this@MainActivity, "Data INSERT SUCCESS", Toast.LENGTH_SHORT).show()
//                }
//                else{
//                    Toast.makeText(this@MainActivity, "Data INSERT FAIL", Toast.LENGTH_SHORT).show()
//                }
//                clearEditText()
//            }
            button.setOnClickListener {
                val name = editPlace.text.toString()
                val result = myDBHelper.findProduct(name)
                if(result){
                    Toast.makeText(this@MainActivity, "RECORD FOUND", Toast.LENGTH_SHORT).show()
                    myDBHelper.showRecord(name)
                    var temploc = LatLng(templat.text.toString().toDouble(), templng.text.toString().toDouble())
                    val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync {
                        googleMap = it
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temploc, 16.0f))
                        googleMap.setMinZoomPreference(10.0f)
                        googleMap.setMaxZoomPreference(18.0f)
                        val option = MarkerOptions()
                        option.position(temploc)
                        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        option.title(name)
                        option.snippet(name+" City Hall")
                        val mk1 = googleMap.addMarker(option)
                        mk1.showInfoWindow()
//            initMapListener()
                    }


                }
                else{
                    Toast.makeText(this@MainActivity, "NO MATCH FOUND", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@MainActivity, DjhAddActivity::class.java)
                    startActivity(intent)

                }
                clearEditText()
            }


        }
    }

    private fun initDB() {
        val dbfile = getDatabasePath("mydb.db")
        if(dbfile.parentFile.exists()){
            dbfile.parentFile.mkdir()
        }
        if(!dbfile.exists()){
            val file = resources.openRawResource(R.raw.mydb)
            val fileSize = file.available()
            val buffer = ByteArray(fileSize)
            file.read(buffer)
            file.close()
            dbfile.createNewFile()
            val output = FileOutputStream(dbfile)
            output.write(buffer)
            output.close()
        }
    }

    private fun initMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(18.0f)
            val option = MarkerOptions()
            option.position(loc)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            option.title("Seoul Station")
            option.snippet("201914185 하동준")
            val mk1 = googleMap.addMarker(option)
            mk1.showInfoWindow()
//            initMapListener()
        }
    }
}