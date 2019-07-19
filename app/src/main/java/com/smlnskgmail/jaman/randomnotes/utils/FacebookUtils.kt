package com.smlnskgmail.jaman.randomnotes.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest


object FacebookUtils {

    @SuppressLint("PackageManagerGetSignatures")
    fun showKey(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(context.packageName,
                PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                Log.e("KEY: ", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("", "", e)
        }
    }

}