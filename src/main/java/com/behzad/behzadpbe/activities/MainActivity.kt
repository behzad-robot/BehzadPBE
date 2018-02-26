package com.behzad.behzadpbe.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.behzad.awesomedialog.AwesomeDialogBuilder
import com.behzad.behzadpbe.R
import com.behzad.behzadpbe.app.GlideApp

import com.behzad.models.User
import com.behzad.myglideview.MyGlideView
import com.behzad.myglideview.indicators.BallBeatIndicator
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.main_page.*

/**
 * Created by behzad on 20/02/18.
 */
@SuppressLint("SetTextI18n")
class MainActivity : BaseActivity(){
    private var compositeDispos : CompositeDisposable? = CompositeDisposable();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page);
        getApp().userManager.listen(19507996, false, object : Observer<User> {
            override fun onSubscribe(d: Disposable) {
                compositeDispos?.add(d)
            }

            override fun onNext(user: User) {
                user_info.text = user.id.toString()+"=>"+user.username;
            }

            override fun onError(e: Throwable) {
                user_info.text = "Error:"+e.message;
            }

            override fun onComplete() {

            }
        });
        getApp().userManager.listen(19507997, false, object : Observer<User> {
            override fun onSubscribe(d: Disposable) {
                compositeDispos?.add(d)
            }

            override fun onNext(user: User) {
                user_info_second.text = user.id.toString()+"=>"+user.username;
                button.setOnClickListener({
                    user.username = "DJ";
                    getApp().userManager.save(user);
                });
                button2.setOnClickListener({
                    user.username = "lucker";
                    getApp().userManager.save(user);
                });
            }

            override fun onError(e: Throwable) {
                user_info.text = "Error:"+e.message;
            }

            override fun onComplete() {

            }
        });
        /*val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()
        val userService = retrofit.create(UserService::class.java)
        val user = userService.getUser("lucker")
        //val finalUserInterest = userInterest
        user.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<User> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(user: User) {
                        Log.e("Main",user.username);
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Main",e.message);
                    }

                    override fun onComplete() {

                    }
                });*/
        button3.setOnClickListener({
            startActivity(Intent(this@MainActivity , MainActivity::class.java));

        });
        button4.setOnClickListener({
            //var dialog : AwesomeDialog = AwesomeDialog(this , ":|" , "hello" , "yes","no");
            //AwesomeDialog(this , ":|" , "hello" , "yes","no" , null);
         /*   var settings : AwesomeDialogSettings = AwesomeDialogSettings();
            settings.title = ":) hello";
            settings.body = "testing body";
            settings.naturalBtn = "DONUT care";
            AwesomeDialog(this , settings).show(); //new AwesomeDialog(this,settings).show();*/

            var a = AwesomeDialogBuilder(this)
                    .title("Hello World")
                    .body("Go fuck ur self")
                    .positiveBtn(":) Yes sure")
                    .negativeBtn(":| ")
                    .build();
            a.show();
        });
        /*GlideApp.with(this)
                .load("http://mobagym.com/ss/")
                .error(R.drawable.ripple_bleach)
                .into(imageView);*/

        MyGlideView(imageView , "http://mobagym.com/ss" , RequestOptions());
        /*val b = BallBeatIndicator()
        b.color = Color.RED
        imageView.setImageDrawable(b)
        b.start()*/

    }

    override fun onDestroy() {
        if(compositeDispos != null)
        {
            compositeDispos?.dispose();
            compositeDispos = null;
        }


        super.onDestroy()
    }
}