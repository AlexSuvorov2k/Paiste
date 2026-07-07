package ru.alexsuvorov.paistewiki.core.support

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics

object Utils {
    @JvmStatic
    fun isTabletDevice(context: Context): Boolean {
        val deviceLarge = (context.resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK ==
                Configuration.SCREENLAYOUT_SIZE_LARGE)
        if (deviceLarge) {
            val metrics = DisplayMetrics()
            val activity = context as Activity
            @Suppress("DEPRECATION")
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT || 
                metrics.densityDpi == DisplayMetrics.DENSITY_HIGH || 
                metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM || 
                metrics.densityDpi == DisplayMetrics.DENSITY_TV || 
                metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun checkIsTablet(context: Context): Boolean {
        return (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    @JvmStatic
    fun checkIsLandscape(context: Context): Boolean {
        val orientation = context.resources.configuration.orientation
        return orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    @JvmStatic
    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                "google_sdk" == Build.PRODUCT)
    }
}
