package at.hagenberg.fh.wc

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual fun getAccelerometerSensor(): AccelerometerSensor {
    TODO("Not yet implemented")
}