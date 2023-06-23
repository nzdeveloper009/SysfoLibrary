package nzcoding.android.sysfolibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import nzcoding.android.sysfo.Sysfo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tag = "nzcoding"

        val systemInfo = Sysfo(applicationContext)

        Log.d(tag, "IP: ${systemInfo.getIPAddress()}")
        Log.d(tag, "MAC: ${systemInfo.getMacAddress()}")
        Log.d(tag, "Device Name: ${systemInfo.getDeviceName()}")
        Log.d(tag, "Carrier Name: ${systemInfo.getCarrierName()}")
        Log.d(tag, "WifiSSID: ${systemInfo.getWifiSSID()}")
        Log.d(tag, "Kernel Version: ${systemInfo.getKernelVersion()}")
        Log.d(tag, "CPU:\n${systemInfo.getCpuInfo()}")
        Log.d(tag, "T RAM: ${systemInfo.getTotalRAM()}")
        Log.d(tag, "F RAM: ${systemInfo.getFreeRAM()}")
        Log.d(tag, "T Storage: ${systemInfo.getTotalInternalStorage()}")
        Log.d(tag, "A Storage: ${systemInfo.getAvailableInternalStorage()}")
        Log.d(tag, "Phone No: ${systemInfo.getPhoneNumber(this)}")
    }
}