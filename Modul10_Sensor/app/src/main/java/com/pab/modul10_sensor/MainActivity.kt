package com.pab.modul10_sensor

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    // Deklarasi variabel
    private lateinit var sensorManager: SensorManager
    private lateinit var proximitySensor: Sensor
    private lateinit var cameraManager: CameraManager
    private lateinit var vibrator: Vibrator
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mainLayout: LinearLayout
    private lateinit var textView: TextView
    private var cameraId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inisialisai Komponen
        setupComponents()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupComponents() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        // Inisialisai Elemen UI
        mainLayout = findViewById(R.id.mainLayout)
        textView = findViewById(R.id.warningTextView)

        // Mendapatkan ID Kamera
        initCameraid()
    }

    // Mendapatkan ID Kamera pertama yang tersedia
    private fun initCameraid() {
        try{
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Mendaftarkan listener sensor
    override fun onResume(){
        super.onResume()
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // Membatalkan pendaftaran listener dan mematikan flash saat aktivitas dijeda
    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
        turnOffFlash()
    }

    // Menangani perubahan sensor
    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] < proximitySensor.maximumRange){
            // Jika Objek Dekat, Aktifkan Alaram
            triggerProximityAlerts()
        } else{
            // Jika Objek Jauh, Reset UI
            resetUI()
        }
    }

    // Mengaktifkan Alarm, Menyalakan Flash, Getaran dll
    private fun triggerProximityAlerts(){
        turnOnFlash()
        vibrator.vibrate(500)
        mainLayout.setBackgroundColor(Color.RED)
        textView.text = "Jarak Terlalu Dekat!"
        playAlarmSound()
    }

    private fun resetUI(){
        turnOffFlash()
        mainLayout.setBackgroundColor(Color.WHITE)
        textView.text = "Proximity Sensor Active"
    }

    override fun onAccuracyChanged(sensor:Sensor, accuracy:Int){

    }

    // Menyalakan Flash
    private fun turnOnFlash(){
        setFlashLight(true)

    }

    // Mematikan Flash
    private fun turnOffFlash(){
        setFlashLight(false)
    }

    // Mengatur Status Flash
    private fun setFlashLight(status: Boolean){
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId!!, status)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    // Memutar Suara alarm jika diputar
    private fun playAlarmSound(){
        if(!mediaPlayer.isPlaying){
            mediaPlayer.start()
        }
    }

    // Melepaskan sumber daya media player saat aktivitas dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}