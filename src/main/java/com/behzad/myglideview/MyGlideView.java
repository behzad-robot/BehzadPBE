package com.behzad.myglideview;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.behzad.behzadpbe.R;
import com.behzad.behzadpbe.app.GlideApp;
import com.behzad.myglideview.indicators.BallBeatIndicator;
import com.behzad.myglideview.indicators.BallPulseIndicator;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by user on 2/26/2018.
 */

public class MyGlideView {
    private ImageView imageView;
    private String url;
    private RequestOptions options;
    public MyGlideView(ImageView imageView, String url, RequestOptions options){
        this.imageView = imageView;
        this.url = url;
        this.options = options;
        load(false);
    }
    private void load(boolean reload){

        imageView.setOnClickListener(v->{});

        BallPulseIndicator b = new BallPulseIndicator();
        b.setColor(reload ?  Color.BLUE : Color.GREEN );
        b.start();
        imageView.setImageDrawable(b);

        GlideApp.with(imageView)
                .load(url)
                .placeholder(b)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        BallBeatIndicator b = new BallBeatIndicator();
                        b.setColor(Color.RED);
                        imageView.setImageDrawable(b);
                        b.start();
                        imageView.setOnClickListener(v->{
                            load(true);
                        });

                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }
}
