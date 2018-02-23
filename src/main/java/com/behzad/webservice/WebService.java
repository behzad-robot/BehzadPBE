package com.behzad.webservice;

import android.content.ContentValues;
import android.os.Build;
import android.util.Log;


import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by behzad on 16/11/16.
 * Version 2.2
 */
/*
    Changelog:
    Version 2.0:
    added support for LOCAL dummy webservices
    2.1:
    added support for time_out,etc error check from outside class
    2.2:
    removed mobagym-only functions and moved such stuff to WebserviceUrl class.

* */
public class WebService
{
    //errors:
    public static final String ERROR_HTTP_CLIENT_TIMEOUT = "time_out";
    public static final String ERROR_HTTP_INTERNAL_ERROR = "server_500";
    public static final String ERROR_HTTP_404_NOT_FOUND= "404_not_found";
    //login token
    //public static String LOGIN_TOKEN = "";


    public static String get(String urlStr, String logCaller) throws Exception
    {
        //throws exception:
        checkInternetAccess();

        Log.e(logCaller,urlStr);
        HttpRequest request = HttpRequest.get(urlStr);
        request.getConnection().setUseCaches(false);
        request.getConnection().setDefaultUseCaches(false);
        //PrintLog.e(logCaller,request.message());
        if(request.ok())
            return request.body();
        else
        {
            String body = request.body();
            if(request.serverError())
                throw new Exception("Internal Server Error 500!");
            else if(!body.matches(""))
                throw new Exception(body);
            else
                throw new Exception(request.message());
        }
    }
    public static String getWithLoginToken(String token, String urlStr, String logCaller) throws Exception
    {
        //throws exception:
        checkInternetAccess();

        Log.e(logCaller,urlStr);
        HttpRequest request = HttpRequest.get(urlStr);
        request.getConnection().setRequestProperty("Authorization","Token "+token);
        request.getConnection().setUseCaches(false);
        request.getConnection().setDefaultUseCaches(false);
        //PrintLog.e(logCaller,request.message());
        if(request.ok())
            return request.body();
        else
        {
            String body = request.body();
            if(request.serverError())
                throw new Exception("Internal Server Error 500!");
            else if(!body.matches(""))
                throw new Exception(body);
            else
                throw new Exception(request.message());
        }
    }
    public static String post(String urlStr, String postData, String logCaller) throws Exception
    {
        //throws exception:
        checkInternetAccess();

        Log.e(logCaller,urlStr);
        HttpRequest request = HttpRequest.post(urlStr);
        request.send(postData);
        if(request.ok() || request.created())
            return request.body();
        else
        {
            String body = request.body();
            if(request.serverError())
                throw new Exception("Internal Server Error 500!");
            else if(!body.matches(""))
                throw new Exception(body);
            else
                throw new Exception(request.message());
        }
    }
    public static String postWithLoginToken(String token, String urlStr, String postData, String logCaller) throws Exception
    {
        //throws exception:
        checkInternetAccess();
        Log.e(logCaller,urlStr);
        HttpRequest request = HttpRequest.post(urlStr);
        request.getConnection().setRequestProperty("Authorization","Token "+token);
        request.send(postData);
        if(request.ok() || request.created())
            return request.body();
        else
        {
            String body = request.body();
            if (!body.matches(""))
                throw new Exception(body);
            else
                throw new Exception(request.message());
        }
    }
    public static String deleteWithLoginToken(String token, String urlStr, String logCaller) throws Exception
    {
        //throws exception:
        checkInternetAccess();
        Log.e(logCaller,urlStr);
        HttpRequest request = HttpRequest.delete(urlStr);
       request.getConnection().setRequestProperty("Authorization","Token "+token);
        request.getConnection().setUseCaches(false);
        request.getConnection().setDefaultUseCaches(false);
        if(request.ok() || request.noContent())
            return request.body();
        else
        {
            Log.e(logCaller, String.valueOf(request.code()));
            String body = request.body();
            if(request.serverError())
                throw new Exception("Internal Server Error 500!");
            else if(!body.matches(""))
                throw new Exception(body);
            else
                throw new Exception(request.message());
        }
    }
    public static String patchWithLoginToken(String token, String urlStr, String postData, String logCaller) throws Exception
    {
        //throws exception:
        checkInternetAccess();
        Log.e(logCaller,urlStr);
        HttpRequest request = (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) ? HttpRequest.patch(urlStr) : HttpRequest.put(urlStr);
        request.getConnection().setRequestProperty("Authorization","Token "+token);
        request.send(postData);
        if(request.ok() || request.created())
            return request.body();
        else
        {
            String body = request.body();
            if (!body.matches(""))
                throw new Exception(body);
            else
                throw new Exception(request.message());
        }
    }
    public static String generatePostData(ContentValues values)
    {
        String postData = "";
        Set<Map.Entry<String, Object>> s=values.valueSet();
        Iterator itr = s.iterator();
        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();
            Log.e("Post-Data",key+"="+ String.valueOf(value));
            try{postData += key+"="+ URLEncoder.encode(String.valueOf(value),"UTF-8")+"&";}
            catch (Exception e){
                Log.e("Post-Data","Failed:"+e.getMessage());}
        }
        postData+= "dummy=dummy";
        return  postData;
    }
    public static String generatePostDataNoEncode(ContentValues values)
    {
        String postData = "";
        Set<Map.Entry<String, Object>> s=values.valueSet();
        Iterator itr = s.iterator();
        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();
            Log.e("Post-Data",key+"="+ String.valueOf(value));
            postData += key+"="+value;
            //try{postData += key+"="+URLEncoder.encode(String.valueOf(value),"UTF-8")+"&";}
            //catch (Exception e){Log.e("Post-Data","Failed:"+e.getMessage());}
        }
        return  postData;
    }
    /*
        try to check internet access and throw exception if we dont have access:
         note: try cos MyApplication.getInstance() or MyApplication.getCurrentActivity() may return null
    * */
    private static void checkInternetAccess() throws Exception {
        //try to check internet access:
        /*if( MyApplication.getInstance() != null && MyApplication.getInstance().getCurrentActivity() != null
                && !MyApplication.getInstance().getCurrentActivity().isOnline() )
            throw new Exception(MyApplication.getInstance().getString(R.string.not_internet_access));*/
    }
}
