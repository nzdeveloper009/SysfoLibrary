# Sysfo
The Sysfo library is a utility library for Android that provides methods to retrieve various system information from an Android device. It offers functionalities such as obtaining the IP address and MAC address of the Wi-Fi connection, retrieving the device name and carrier name, getting the SSID of the connected Wi-Fi network, obtaining the kernel version, CPU information, RAM usage, and internal storage information.

Additionally, the library includes a method to retrieve the phone number associated with the device, which requires the appropriate permission. It handles the case when the permission is not granted by requesting it from the user.

The library aims to simplify the process of accessing system information and provides an easy-to-use interface for retrieving relevant details about the device. It handles different aspects of system information retrieval, including Wi-Fi, telephony, kernel, CPU, RAM, and storage.

It's important to note that the availability and accuracy of certain information may vary depending on the device's configuration and Android version. The library addresses these variations and provides fallback mechanisms to handle cases where certain information may not be available.

Overall, the Sysfo library provides a convenient way to access and utilize system information in Android applications, enabling developers to build more feature-rich and informed applications.
## Steps to use features in the project:
> Step 1. Add the JitPack repository to your build file<br />Add it in your root **build.gradle** at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
> Step 2. Add the dependency
```gradle
dependencies {
  implementation 'com.github.nzdeveloper009:SysfoLibrary:1.0.0'
}
```
