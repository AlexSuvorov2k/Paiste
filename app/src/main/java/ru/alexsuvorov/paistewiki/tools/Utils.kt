package ru.alexsuvorov.paistewiki.tools

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics

object Utils {
    fun isTabletDevice(context: Context): Boolean {
        val device_large = ((context.getResources().getConfiguration().screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE)

        if (device_large) {
            val metrics = DisplayMetrics()
            val activity = context as Activity
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics)

            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM || metrics.densityDpi == DisplayMetrics.DENSITY_TV || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                return true
            }
        }
        return false
    }

    fun checkIsTablet(context: Context): Boolean {
        return (context.getResources().getConfiguration().screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun checkIsLandscape(context: Context): Boolean {
        val orientation = context.getResources().getConfiguration().orientation
        return (orientation == Configuration.ORIENTATION_LANDSCAPE)
    }

    val isEmulator: Boolean
        get() = Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk" == Build.PRODUCT
}
