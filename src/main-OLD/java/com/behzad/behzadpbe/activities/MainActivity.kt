package com.behzad.behzadpbe.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.behzad.behzadpbe.R
import com.behzad.models.User
import com.squareup.haha.perflib.Main
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
        getApp().userManager.listen("behzad-robot", false, object : Observer<User> {
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
        getApp().userManager.listen("behzad-robot", false, object : Observer<User> {
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
        })
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

        })
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