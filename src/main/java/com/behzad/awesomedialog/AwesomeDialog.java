package com.behzad.awesomedialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.behzad.behzadpbe.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by behzad on 22/02/18.
 */
/*
    AwesomeDialog:
    {
        show() -> show anim + show custom view anim with it's own delay -> show dialog anim -> show buttons anim
        hide() -> hide buttons anim + hide custom view anim with it's own delay  -> hide dialog anim -> hide anim
        //So u can put ur custom view's animation before/after watever u want. :)
        //look at R.layout.awesome_alert for example of a good layout :) ids must match.
    }
* */
public class AwesomeDialog
{
    private Context context;
    private View view;
    private View alertView;
    private View overlay;
    private TextView titleView , bodyView , postiveBtnView , negativeBtnView , naturalBtnView;
    private FrameLayout customViewContainer;

    private AwesomeDialogSettings settings;
    public AwesomeDialog(Context context,AwesomeDialogSettings settings)
    {
        this.context = context;
        this.settings = settings;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate , setup:
        view = inflater.inflate(settings.resourceID, null);
        alertView = view.findViewById(R.id.awesome_dialog);
        overlay = view.findViewById(R.id.awesome_dialog_overlay);
        titleView = view.findViewById(R.id.awesome_dialog_title);
        bodyView = view.findViewById(R.id.awesome_dialog_body);
        customViewContainer = view.findViewById(R.id.awesome_dialog_custom_view);

        postiveBtnView = view.findViewById(R.id.awesome_dialog_btn_positive);
        negativeBtnView = view.findViewById(R.id.awesome_dialog_btn_negative);
        naturalBtnView = view.findViewById(R.id.awesome_dialog_btn_natural);


        if(settings.title != null && !settings.title.matches(""))
            titleView.setText(settings.title);
        else
            titleView.setVisibility(View.GONE);
        if (settings.body != null && !settings.body.matches(""))
            bodyView.setText(settings.body);
        else
            bodyView.setVisibility(View.GONE);

        if(!AwesomeDialogSettings.isEmpty(settings.positiveBtn))
            postiveBtnView.setText(settings.positiveBtn);
        else
            postiveBtnView.setVisibility(View.GONE);

        if(!AwesomeDialogSettings.isEmpty(settings.negativeBtn))
            negativeBtnView.setText(settings.negativeBtn);
        else
            negativeBtnView.setVisibility(View.GONE);

        if(!AwesomeDialogSettings.isEmpty(settings.naturalBtn))
            naturalBtnView.setText(settings.naturalBtn);
        else
            naturalBtnView.setVisibility(View.GONE);

        if(settings.customView != null)
            customViewContainer.addView(settings.customView);

        /*YoYo.with(Techniques.RubberBand)
                .duration(400)
                .delay(100)
                .playOn(alertView);*/
        /**/




        //hide:
        overlay.setOnClickListener(v -> { hide(); });
    }
    //region public interface
    public void show() {
        //override null values:
        if(settings.showAnim == null)
            settings.showAnim = getDefaultShowAnim();
        if(settings.showDialogAnim == null)
            settings.showDialogAnim = getDefaultShowDialogAnim();
        if(settings.showButtonAnim == null)
            settings.showButtonAnim = getDefaultShowButtonAnim();
        if(settings.showCustomViewAnim == null)
            settings.showCustomViewAnim = getEmptyAnim();
        //add view:
        addView();
        //show anim:
        settings.showAnim.animate(view);
        alertView.setVisibility(View.INVISIBLE);
        //show custom view anim:
        if(settings.customView != null)
        {
            customViewContainer.postDelayed(()->{
                settings.showCustomViewAnim.animate(customViewContainer);
            },settings.showCustomViewAnim.getDuration());
        }
        view.postDelayed(() -> {
            //next show alert anim:
            alertView.setVisibility(View.VISIBLE);
            settings.showDialogAnim.animate(alertView);
            alertView.postDelayed(()->{
                //next show button animations:
                if(!AwesomeDialogSettings.isEmpty(settings.positiveBtn))
                    settings.showButtonAnim.animate(postiveBtnView);
                if(!AwesomeDialogSettings.isEmpty(settings.negativeBtn))
                    settings.showButtonAnim.animate(negativeBtnView);
                if(!AwesomeDialogSettings.isEmpty(settings.naturalBtn))
                    settings.showButtonAnim.animate(naturalBtnView);

            },settings.showDialogAnim.getDuration());

        }, settings.showAnim.getDuration());
    }
    public void hide() {

        //override null values:
        if(settings.hideAnim == null)
            settings.hideAnim = getDefaultHideAnim();
        if(settings.hideDialogAnim == null)
            settings.hideDialogAnim = getDefaultHideDialogAnim();
        if(settings.hideButtonAnim == null)
            settings.hideButtonAnim = getDefaultHideButtonAnim();
        if(settings.hideCustomViewAnim == null)
            settings.hideCustomViewAnim = getEmptyAnim();
        //hide button animations:
        if(!AwesomeDialogSettings.isEmpty(settings.positiveBtn))
            settings.hideButtonAnim.animate(postiveBtnView);
        if(!AwesomeDialogSettings.isEmpty(settings.negativeBtn))
            settings.hideButtonAnim.animate(negativeBtnView);
        if(!AwesomeDialogSettings.isEmpty(settings.naturalBtn))
            settings.hideButtonAnim.animate(naturalBtnView);
        //hide custom view anim:
        if(settings.customView != null)
        {
            customViewContainer.postDelayed(()->{
                settings.hideCustomViewAnim.animate(customViewContainer);
            },settings.hideCustomViewAnim.getDuration());
        }

        view.postDelayed(()->{

            //hide dialog anim:
            settings.hideDialogAnim.animate(alertView);
            alertView.postDelayed(()->{
                alertView.setVisibility(View.INVISIBLE);
                //hide screen:
                settings.hideAnim.animate(view);
                view.postDelayed(this::removeView,settings.hideAnim.getDuration());

            },settings.hideDialogAnim.getDuration());

        },settings.hideButtonAnim.getDuration());
    }
    public AwesomeDialogSettings getSettings(){
        return settings;
    }
    //endregion

    //helpers:
    private void addView(){
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        //attach to window:
        getWindowManager().addView(view, params);
    }
    private void removeView(){
        getWindowManager().removeView(view);
    }
    private WindowManager getWindowManager(){
        return  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    //default animations:
    static AwesomeDialogAnimation getDefaultShowAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                YoYo.with(Techniques.FadeIn)
                        .duration(500)
                        .playOn(view);
            }

            @Override
            public long getDuration() {
                return 500;
            }
        };
    }
    static AwesomeDialogAnimation getDefaultShowDialogAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                /*YoYo.with(Techniques.BounceIn)
                        .duration(500)
                        .playOn(view);*/
                Animation animation = AnimationUtils.loadAnimation(view.getContext(),android.R.anim.slide_in_left);
                view.startAnimation(animation);
            }

            @Override
            public long getDuration() {
                return 400;
            }
        };
    }
    static AwesomeDialogAnimation getDefaultShowButtonAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                YoYo.with(Techniques.RubberBand)
                        .duration(500)
                        .playOn(view);
            }

            @Override
            public long getDuration() {
                return 500;
            }
        };
    }
    static AwesomeDialogAnimation getDefaultHideAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                YoYo.with(Techniques.FadeOut)
                        .duration(500)
                        .playOn(view);
            }

            @Override
            public long getDuration() {
                return 500;
            }
        };
    }
    static AwesomeDialogAnimation getDefaultHideDialogAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(),android.R.anim.slide_out_right);
                view.startAnimation(animation);
            }

            @Override
            public long getDuration() {
                return 400;
            }
        };
    }
    static AwesomeDialogAnimation getDefaultHideButtonAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {
                YoYo.with(Techniques.RubberBand)
                        .duration(500)
                        .playOn(view);
            }

            @Override
            public long getDuration() {
                return 500;
            }
        };
    }
    static AwesomeDialogAnimation getEmptyAnim(){
        return new AwesomeDialogAnimation() {
            @Override
            public void animate(View view) {

            }

            @Override
            public long getDuration() {
                return 0;
            }
        };
    }

}
