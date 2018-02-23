package com.behzad.behzadpbe.managers;

import com.behzad.behzadpbe.app.MyApplication;
import com.behzad.models.User;
import com.google.gson.Gson;

import com.snatik.storage.Storage;

import java.io.File;

/**
 * Created by behzad on 12/02/18.
 */

public class UsersStorageNew
{
    private static final String LOGIN_USERS_DIRECTORY="login-users";
    private static final String CACHE_USERS_DIRECTORY="users";
    private Storage storage;
    public UsersStorageNew(MyApplication app)
    {
        storage = new Storage(app);
        checkDirectory();
    }

    private String getDirectory(){return storage.getInternalFilesDirectory()+ File.separator+CACHE_USERS_DIRECTORY;}
    private void checkDirectory()
    {
        if(!storage.isDirectoryExists( getDirectory() ))
            storage.createDirectory( getDirectory() );
    }

    public void clearDirectory()
    {
        storage.deleteDirectory(getDirectory());
    }

    public boolean hasUser(long id)
    {
        return storage.isFileExist(getDirectory()+ File.separator+"user-"+ String.valueOf(id)+".json");
    }
    public User loadUser(long id)
    {
        if(!hasUser(id))
            return null;
        String json = storage.readTextFile(getDirectory()+ File.separator+"user-"+ String.valueOf(id)+".json");
        return User.create(new Gson(),json);
    }
    public void saveUser(User user)
    {
        storage.createFile(getDirectory()+ File.separator+"user-"+ String.valueOf(user)+".json", new Gson().toJson(user));
    }

}
