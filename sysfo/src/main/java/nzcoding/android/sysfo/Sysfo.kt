package nzcoding.android.sysfo

import android.Manifest.permission.READ_PHONE_NUMBERS
import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.telephony.TelephonyManager
import android.text.format.Formatter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Sysfo(private val context: Context) : SysInfo {
    override fun getIPAddress(): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo
        val ipAddress = wifiInfo?.ipAddress ?: 0
        return Formatter.formatIpAddress(ipAddress)
    }

    @SuppressLint("HardwareIds")
    override fun getMacAddress(): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo
        return wifiInfo?.macAddress ?: ""
    }

    override fun getDeviceName(): String = Build.MODEL


    override fun getCarrierName(): String {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkOperatorName ?: ""
    }

    override fun getWifiSSID(): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true) {
            val wifiInfo = wifiManager.connectionInfo
            wifiInfo.ssid.replace("\"", "")
        } else {
            "Not Connected"
        }
    }

    override fun getKernelVersion(): String = System.getProperty("os.version")!!

    override fun getCpuInfo(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Supported ABIs: ").append(Build.SUPPORTED_ABIS.joinToString())
            .append("\n")
        stringBuilder.append("Supported 32-bit ABIs: ")
            .append(Build.SUPPORTED_32_BIT_ABIS.joinToString()).append("\n")
        stringBuilder.append("Supported 64-bit ABIs: ")
            .append(Build.SUPPORTED_64_BIT_ABIS.joinToString()).append("\n")
        return stringBuilder.toString()
    }

    override fun getTotalRAM(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(memoryInfo)
        return "${memoryInfo.totalMem / (1024 * 1024 * 1024)} GB" // Convert to GB
    }

    override fun getFreeRAM(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(memoryInfo)
        return "${memoryInfo.availMem / (1024 * 1024)} MB" // Convert to MB
    }

    override fun getTotalInternalStorage(): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize: Long = stat.blockSizeLong
        val totalBlocks: Long = stat.blockCountLong
        val totalStorage = blockSize * totalBlocks
        val totalStorageGB = totalStorage / (1024 * 1024 * 1024) // Convert to GB
        return "$totalStorageGB GB"
    }

    override fun getAvailableInternalStorage(): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize: Long = stat.blockSizeLong
        val availableBlocks: Long = stat.availableBlocksLong
        val availableStorage = blockSize * availableBlocks
        val availableStorageGB = availableStorage / (1024 * 1024 * 1024) // Convert to GB
        return "$availableStorageGB GB"
    }

    private val READ_PHONE_STATE_PERMISSION_CODE = 1001

    fun hasReadPhoneStatePermission(): Boolean {
        val permission = READ_PHONE_STATE
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestReadPhoneStatePermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(READ_PHONE_STATE, READ_PHONE_NUMBERS),
                READ_PHONE_STATE_PERMISSION_CODE
            )
        } else {
            arrayOf(READ_PHONE_STATE)
            READ_PHONE_STATE_PERMISSION_CODE
        }
    }

    @SuppressLint("HardwareIds")
    override fun getPhoneNumber(activity: Activity): String {
        return if (hasReadPhoneStatePermission()) {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            try {
                telephonyManager.line1Number ?: ""
            } catch (e: SecurityException) {
                e.printStackTrace()
                "Permission Not Granted"
            }
        } else {
            requestReadPhoneStatePermission(activity)
            "Permission Not Granted"
        }
    }


    private fun convertBytesToGB(bytes: Long): Long {
        val gigabyte = 1024 * 1024 * 1024
        return bytes / gigabyte
    }
}