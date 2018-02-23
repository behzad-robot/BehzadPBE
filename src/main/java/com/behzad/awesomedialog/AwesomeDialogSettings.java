package com.behzad.awesomedialog;

import android.view.View;

import com.behzad.behzadpbe.R;

/**
 * Created by behzad on 22/02/18.
 */

public class AwesomeDialogSettings
{
    public int resourceID = R.layout.awesome_alert;

    public String title;
    public String body;
    public String negativeBtn;
    public String positiveBtn;
    public String naturalBtn;
    public AwesomeDialogAnimation showAnim;
    public AwesomeDialogAnimation showDialogAnim;
    public AwesomeDialogAnimation showButtonAnim;
    public AwesomeDialogAnimation hideAnim;
    public AwesomeDialogAnimation hideDialogAnim;
    public AwesomeDialogAnimation hideButtonAnim;

    public View customView;
    public AwesomeDialogAnimation showCustomViewAnim;
    public AwesomeDialogAnimation hideCustomViewAnim;



    public static boolean isEmpty(String str){
        return str == null || str.equals("");
    }
}
