package com.behzad.behzadpbe.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.behzad.behzadpbe.app.MyApplication
import com.behzad.behzadpbe.managers.UserManager

/**
 * Created by behzad on 20/02/18.
 */
open class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun getApp():MyApplication{
        return application as MyApplication;
    }
    
    //val app : MyApplication = application as MyApplication;
}