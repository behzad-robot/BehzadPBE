package com.behzad.behzadpbe.app

import android.app.Application
import com.behzad.behzadpbe.managers.ServiceManager
import com.behzad.behzadpbe.managers.UserManager

import com.squareup.leakcanary.LeakCanary

/**
 * Created by behzad on 20/02/18.
 */
class MyApplication : Application() {
    lateinit var userManager : UserManager;
    lateinit var apiManager: ServiceManager;
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this))
            return
        LeakCanary.install(this)
        userManager = UserManager(this);
        apiManager = ServiceManager(this);
    }
}