package com.behzad.awesomedialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by behzad on 23/02/18.
 */

public class AwesomeDialogBuilder
{
    private AwesomeDialogSettings settings;
    private Context context;

    public AwesomeDialogBuilder(Context context)
    {
        this.context = context;
        settings = new AwesomeDialogSettings();
        this.settings.showButtonAnim = AwesomeDialog.getEmptyAnim();
        this.settings.hideButtonAnim = AwesomeDialog.getEmptyAnim();
    }
    public AwesomeDialog build(){
        return new AwesomeDialog(context , settings);
    }

    //TODO:implement for all fields: all functions must return this;
    public AwesomeDialogBuilder showAnim(AwesomeDialogAnimation showAnim){
        this.settings.showAnim = showAnim;
        return this;
    }
    //end
    //region customized , ready dialogs:
    public AwesomeDialogBuilder defaultTest(){
        this.settings = new AwesomeDialogSettings();
        return this;
    }
    public AwesomeDialogBuilder slideHorizontal(){
        this.settings.showAnim = null;
        this.settings.showDialogAnim = null;
        this.settings.hideAnim = null;
        this.settings.hideDialogAnim = null;
        return this;
    }
    public AwesomeDialogBuilder slideVertical(){
        this.settings.showAnim = null;
        this.settings.showDialogAnim = new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), );
                view.startAnimation(animation);
            }

            @Override
            public long getDuration() {
                return 400;
            }
        };
        this.settings.hideAnim = null;
        this.settings.hideDialogAnim = null;
    }
    public AwesomeDialogBuilder fade(){
        this.settings.showAnim = null;
        this.settings.showDialogAnim = AwesomeDialog.getDefaultShowAnim();
        this.settings.hideAnim = null;
        this.settings.hideDialogAnim = AwesomeDialog.getDefaultHideAnim();
        return  this;
    }

    //endregion
}
