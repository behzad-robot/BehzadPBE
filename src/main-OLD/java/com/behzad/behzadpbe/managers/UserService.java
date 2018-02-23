package com.behzad.behzadpbe.managers;

import com.behzad.models.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by behzad on 20/02/18.
 */

public interface UserService {
    @GET("users/{username}")
    Observable<User> getUser(@Path("username") String username);
}
