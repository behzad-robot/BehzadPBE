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
    public AwesomeDialogBuilder title(String title){
        this.settings.title = title;
        return this;
    }
    public AwesomeDialogBuilder body(String body){
        this.settings.body = body;
        return this;
    }
    public AwesomeDialogBuilder negativeBtn(String negativeBtn){
        this.settings.negativeBtn = negativeBtn;
        return this;
    }
    public AwesomeDialogBuilder naturalBtn(String naturalBtn){
        this.settings.positiveBtn = naturalBtn;
        return this;
    }
    public AwesomeDialogBuilder positiveBtn(String positiveBtn){
        this.settings.positiveBtn = positiveBtn;
        return this;
    }

    public AwesomeDialogBuilder showAnim(AwesomeDialogAnimation showAnim){
        this.settings.showAnim = showAnim;
        return this;
    }
    public AwesomeDialogBuilder showDialogAnim(AwesomeDialogAnimation showDialogAnim){
        this.settings.showDialogAnim=showDialogAnim;
        return this;
    }
    public AwesomeDialogBuilder showButtonAnim(AwesomeDialogAnimation showButtonAnim){
        this.settings.showButtonAnim=showButtonAnim;
        return this;
    }
    public AwesomeDialogBuilder hideAnim(AwesomeDialogAnimation showButtonAnim){
        this.settings.showButtonAnim=showButtonAnim;
        return this;
    }
    public AwesomeDialogBuilder hideDialogAnim(AwesomeDialogAnimation hideDialogAnim){
        this.settings.hideDialogAnim=hideDialogAnim;
        return this;
    }
    public AwesomeDialogBuilder hideButtonAnim(AwesomeDialogAnimation hideButtonAnim){
        this.settings.hideButtonAnim=hideButtonAnim;
        return this;
    }
    public AwesomeDialogBuilder showCustomViewAnim(AwesomeDialogAnimation showCustomViewAnim){
        this.settings.showCustomViewAnim=showCustomViewAnim;
        return this;
    }
    public AwesomeDialogBuilder hideCustomViewAnim(AwesomeDialogAnimation hideCustomViewAnim){
        this.settings.hideCustomViewAnim=hideCustomViewAnim;
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
    public AwesomeDialogBuilder slideFromTop(){
        this.settings.showAnim = null;
        this.settings.showDialogAnim = new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                /*Animation animation = AnimationUtils.loadAnimation(view.getContext(), );
                view.startAnimation(animation);*/
            }

            @Override
            public long getDuration() {
                return 400;
            }
        };
        this.settings.hideAnim = null;
        this.settings.hideDialogAnim = null;
        return this;
    }
    //TODO: slideFromBottom()

    public AwesomeDialogBuilder fade(){
        this.settings.showAnim = null;
        this.settings.showDialogAnim = AwesomeDialog.getDefaultShowAnim();
        this.settings.hideAnim = null;
        this.settings.hideDialogAnim = AwesomeDialog.getDefaultHideAnim();
        return  this;
    }

    //endregion
}
