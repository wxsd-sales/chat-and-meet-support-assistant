package com.example.webexandroid

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private var mHandler: Handler? = null

    // default interval for syncing data
    val DEFAULT_SYNC_INTERVAL = (2 * 1000).toLong()

    // task to be run here
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            syncData()
            // Repeat this runnable code block again every ... min
            mHandler?.postDelayed(this, DEFAULT_SYNC_INTERVAL)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create the Handler object
        mHandler = Handler()
        // Execute a runnable task as soon as possible
        mHandler!!.post(runnableService)
        return START_STICKY
    }

    @Synchronized
    private fun syncData() {
        // call your rest service here
    }
}