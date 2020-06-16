package com.example.website.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.website.model.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my shared_preff";
    private static SharedPrefManager mInstance;
    private Context context;

    private SharedPrefManager(Context context) {
        this.context = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUser(String id,String name,String mail) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id",id);
        editor.putString("username",name);
        editor.putString("email",mail);

        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id",null)!=null;
    }


    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
