package com.stout.basketball.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;
import com.stout.basketball.Models.Player;

/**
 * Created by TJ Frisch on 11/20/2016.
 */

public final class GsonUtility {


    //Private body to prevent instantiation.
    private GsonUtility(){

    }

    /**
     * Generic save method to store ArrayLists to the SharedPreferences.
     * @param array - The array to be stored.
     * @param key - The key for the array to be stored on.
     * @param context - The context of the application.
     */
    public static void saveArrayListToStorage(ArrayList array, String key,Context context){
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(array);
        prefsEditor.putString(key,json);
        prefsEditor.commit();

    }

    /**
     * Generic fetch method to retrieve a Player Array
     * //TODO Extend this method to abstract to other data types.
     * @param key - The key of which the ArrayList is stored on in SharedPreferences.
     * @param context - The context of the application.
     * @return
     */
    public static ArrayList<Player> fetchArrayListFromStorage(String key, Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String playerListJson = mPrefs.getString(key,"");
        Type playerListType = new TypeToken<ArrayList<Player>>() {}.getType();
        ArrayList<Player> myPlayerList = (gson.fromJson(playerListJson, playerListType));
        if(myPlayerList == null){
            //Ensure there is no "null" arraylist.
            myPlayerList = new ArrayList<Player>();
        }
        return myPlayerList;
    }
}
