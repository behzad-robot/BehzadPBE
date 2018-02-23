package com.behzad.webservice;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by behzad on 26/11/16.
 */

public abstract class MyAsyncTask extends AsyncTask<String,Integer,String>
{
    protected String message;
    protected  boolean success;

    protected String LOG_TAG;
    public MyAsyncTask()
    {
        LOG_TAG = this.getClass().getSimpleName();
        success = true;
    }
    protected  void LogException(Exception e)
    {
        Log.e(LOG_TAG,"Failed from try/catch: "+e.getMessage());
        success = false;
        message = e.getMessage();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.e(LOG_TAG,"onCancelled()");
    }
}
