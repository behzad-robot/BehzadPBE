package com.behzad.webservice;

/**
 * Created by behzad on 30/06/17.
 */

public class WebserviceUrl
{
    public static  final String APP_INFO_FILE = "http://mobagym.com/media/mobagym-app-info/app-info.json";
    public static  final String GAMES_HOME_TAB_FILE = "http://mobagym.com/media/mobagym-app-info/games-tab-info.json";
    public static  final String SHOP_INFO_FILE = "http://mobagym.com/media/mobagym-app-info/shop-info.json";

    public static final String WEBSERVICE_URL_BASE = "http://mobagym.com/api/";

    public static  final String SITE_URL_BASE ="http://mobagym.com/";
    public static  final String LOCALHOST_BASE = "http://10.0.2.2/";
    public static final String FILE_BASE = "file:///android_asset/";


    //webservices:
    public static String getWebService(String name)
    {
        return WEBSERVICE_URL_BASE+name+"/";
    }




}
