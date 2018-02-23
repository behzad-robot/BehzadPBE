package com.behzad.behzadpbe.activities;

import android.os.Bundle;

import com.behzad.models.User;

import org.jetbrains.annotations.Nullable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by behzad on 20/02/18.
 */

public class JavaAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisposableObserver<User> ob= new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        ob.dispose();

        getApp().getUserManager().listen("behzad-robot", false, ob);
    }
}
