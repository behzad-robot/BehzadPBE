package com.behzad.behzadpbe.managers;



import android.util.Log;

import com.behzad.behzadpbe.app.MyApplication;
import com.behzad.models.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by behzad on 12/02/18.
 */

public class UserManager
{




    private MyApplication app;
    private UsersStorageNew userStorage;


    public UserManager(MyApplication app)
    {
        this.app = app;
        this.userStorage = new UsersStorageNew(app);
        observables = new ArrayList<>();
        users = new ArrayList<>();
    }
    public class UserInterest
    {
        public String username;
        public PublishSubject<User> subject;
        public User user;
        //public PublishSubject<User> publishSubject;
        public UserInterest(String username){
            this.username = username;
            subject = PublishSubject.create();
            //publishSubject = PublishSubject.create();
        }
    }
    ///ACTUAL CONTEXT =>
    private  static final String LOG_TAG = "User-Manager";
    private ArrayList<UserInterest> observables;
    private ArrayList<User> users;

    private User getFromCache(String username)
    {
        if(users == null)
            users = new ArrayList<>();
        for(int i = 0 ; i <  users.size() ; i++)
            if(users.get(i).username.equals(username))
                return users.get(i);
        return null;
    }
    public void listen(final String username,final boolean forceWebLoad, Observer<User> observer)
    {
        UserInterest userInterest = null;
        //if interest already exists listen to it:
        for(int i = 0 ; i < observables.size() ; i++)
        {
            if(observables.get(i).username.equals(username))
            {
                Log.e(LOG_TAG , "Already had interest");
                observables.get(i).subject
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
                userInterest = observables.get(i);
                if(!forceWebLoad)
                {
                    //cold observer effect
                    /*if(userInterest.subject.user != null)
                        observer.onNext(userInterest.subject.user);*/
                    /*if(userInterest.user != null)
                        observer.onNext(userInterest.user);*/
                    return;
                }

            }
        }
        //else create interest,listen to it:
        if(userInterest == null)
        {
            Log.e(LOG_TAG , "Create interest");
            userInterest = new UserInterest(username);
            observables.add(userInterest);
            //listen to interest:
            userInterest.subject
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
        Log.e(LOG_TAG , ":| listening...");
        //check cache:
        if(!forceWebLoad && getFromCache(username) != null)
        {
            userInterest.subject.onNext( getFromCache(username));
            return;
        }
        //check storage:
       /* if(!forceWebLoad && userStorage.hasUser(username))
        {
            userInterest.subject.onNext(userStorage.loadUser(userId));
            return;
        }*/
        //io.reactivex.Observable.fromCallable()
        //load from webservice:
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        UserService userService = retrofit.create(UserService.class);
        Observable<User> user = userService.getUser("behzad-robot");
        UserInterest finalUserInterest1 = userInterest;
        user.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.e(LOG_TAG,user.username);
                        users.add(user);
                        userStorage.saveUser(user);
                        finalUserInterest1.subject.onNext(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG , e.getMessage());
                        finalUserInterest1.subject.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*try
        {
            User user = UserSingleLoader.load(String.valueOf(userId)+"/", LOG_TAG);
            Log.e(LOG_TAG , user.username);
            userStorage.saveUser(user);
            users.add(user);
            userInterest.subject.onNext(user);
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG , ":| wrong=>"+e.getMessage());
            userInterest.subject.onError(e);
        }*/
    }
    public void save(User user){
        //update in storage:
        userStorage.saveUser(user);
        //update in ram:
        for(int i = 0 ; i < users.size() ; i++)
        {
            if(users.get(i).id == user.id)
            {
                users.remove(i);
                users.add(user);
                break;
            }
        }
        //update for observers:
        /*for(int i = 0 ; i < observables.size() ; i++)
        {
            if(observables.get(i).subject.user.id == user.id)
            {
                observables.get(i).subject.user =  user;
                observables.get(i).subject.onNext(user);
            }
        }*/

    }
    public class SU extends Observable<User>
    {
        @Override
        protected void subscribeActual(Observer<? super User> observer) {
            observer.onNext(new User());

        }

    }

    public class UserSubject extends Subject<User>
    {
        public User user;
        private ArrayList<Observer<? super User>> observers = new ArrayList<>();

        @Override
        public boolean hasObservers() {
            return observers.size() != 0;
        }

        @Override
        public boolean hasThrowable() {
            return false;
        }

        @Override
        public boolean hasComplete() {
            return false;
        }

        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        protected void subscribeActual(Observer<? super User> observer) {
            observers.add(observer);

        }

        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(User user) {
            this.user = user;
            Log.e(LOG_TAG , "on next Called with "+user.username+" for "+String.valueOf(observers.size())+" observers");
            for(int i = 0 ; i < observers.size();i++)
                if(observers.get(i) == null)
                    observers.remove(i);
            for(int i = 0 ; i < observers.size();i++)
                if(observers.get(i) != null)
                    observers.get(i).onNext(user);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
        }

    }


}
