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

/**
 * Created by behzad on 12/02/18.
 */

public class UserManager
{
    private  static final String LOG_TAG = "User-Manager";

    private MyApplication app;
    private UserStorage userStorage;
    private ArrayList<User> users;

    private ArrayList<UserInterest> observables;

    public UserManager(MyApplication app)
    {
        this.app = app;
        this.userStorage = new UserStorage(app);
        observables = new ArrayList<>();
        users = new ArrayList<>();
    }
    public class UserInterest
    {
        public long userID;
        private User user;
        public PublishSubject<User> subject;
        //public PublishSubject<User> publishSubject;
        public UserInterest(long userID){
            this.userID = userID;
            subject = PublishSubject.create();
        }
        public void setUser(User user){
            this.user = user;
            this.userID = user.id;
        }
    }
    //cache functions:
    private User getFromCache(long userId)
    {
        if(users == null)
            users = new ArrayList<>();
        for(int i = 0 ; i <  users.size() ; i++)
            if(users.get(i).id == userId)
                return users.get(i);
        return null;
    }
    private User getFromCache(String username)
    {
        if(users == null)
            users = new ArrayList<>();
        for(int i = 0 ; i <  users.size() ; i++)
            if(users.get(i).username.equals(username))
                return users.get(i);
        return null;
    }
    //region public interface
    public void listen(final long userId,final boolean forceWebLoad, Observer<User> observer)
    {
        UserInterest userInterest = null;
        //if interest already exists listen to it:
        for(int i = 0 ; i < observables.size() ; i++)
        {
            if(observables.get(i).userID == userId)
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
                    if(userInterest.user != null)
                        observer.onNext(userInterest.user);
                    return;
                }

            }
        }
        //else create interest,listen to it:
        if(userInterest == null)
        {
            Log.e(LOG_TAG , "Create interest");
            userInterest = new UserInterest(userId);
            observables.add(userInterest);
            //listen to interest:
            userInterest.subject
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
        Log.e(LOG_TAG , ":| listening...");
        //check cache:
        if(!forceWebLoad && getFromCache(userId) != null)
        {
            userInterest.subject.onNext(getFromCache(userId));
            userInterest.setUser(getFromCache(userId));
            return;
        }
        //check storage:
        if(!forceWebLoad && userStorage.hasUser(userId) && userStorage.loadUser(userId) != null)
        {
            userInterest.subject.onNext(userStorage.loadUser(userId));
            userInterest.setUser(getFromCache(userId));
            return;
        }
        //load from webservice:
        String username = "behzad-robot";
        if(userId == 19507996)
            username = "behzad-robot";
        else if(userId == 19507997)
            username = "hillbllmark11";
        else if(userId == 19507998)
            username = "liorbeny";
        else
            username = "behzad-robot";
        Observable<User> user = app.apiManager.userApi().getUser(username);
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
                        finalUserInterest1.setUser(user);
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
        for(int i = 0 ; i < observables.size() ; i++)
        {
            if(observables.get(i).userID == user.id)
            {
                observables.get(i).setUser(user);
                observables.get(i).subject.onNext(user);
            }
        }
    }
    //endregion
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
