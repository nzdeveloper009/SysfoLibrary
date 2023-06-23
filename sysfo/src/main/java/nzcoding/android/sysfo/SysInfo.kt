package nzcoding.android.sysfo

import android.app.Activity

interface SysInfo {
    fun getIPAddress(): String
    fun getMacAddress(): String
    fun getDeviceName(): String
    fun getCarrierName(): String
    fun getWifiSSID(): String
    fun getKernelVersion(): String
    fun getCpuInfo(): String
    fun getTotalRAM(): String
    fun getFreeRAM(): String
    fun getTotalInternalStorage(): String
    fun getAvailableInternalStorage(): String
    fun getPhoneNumber(activity: Activity): String
}