package com.behzad.models;

import android.util.Log;

import com.behzad.webservice.MyAsyncTask;
import com.behzad.webservice.WebService;
import com.behzad.webservice.WebserviceUrl;
import com.google.gson.Gson;

/**
 * Created by behzad on 20/02/18.
 */

public class UserSingleLoader extends MyAsyncTask {
    private String params;
    private OnUserResult onUserResult;
    private User user;
    public UserSingleLoader(String params,OnUserResult onUserResult){
        this.params = params;
        this.onUserResult = onUserResult;
    }
    @Override
    protected String doInBackground(String... strings) {
        try
        {
            user = load(params,LOG_TAG);
        }
        catch (Exception e){
            LogException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public static User load(String params,String LOG_TAG) throws Exception
    {
        String result = WebService.get(WebserviceUrl.getWebService("auth/users")+params,LOG_TAG);
        Log.e(LOG_TAG ,result);
        return User.create(new Gson(), result);
    }
    public interface OnUserResult
    {
        void success(User user);
        void failed(String message);
    }
}
