package com.seanmaraia.sean_mbp.listdemospm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Sean-MBP on 9/14/15.
 */
public class DataStore {
    private static DataStore sDataStore;
    private ArrayList<Meal> mData;
    private int mNumTimesRun;

    public static final String PREFS_NAME = "DATA_STORE_PREFERENCES";
    public static final String KEY_NUM_TIMES_RUN = "NUM_TIMES_RUN";
    public static final String KEY_ITEMS_STRING = "ITEMS_STRING";

    //getter
    public static DataStore get(Context context) {
        if(sDataStore == null) {
            sDataStore = new DataStore(context);
        }
        return sDataStore;
    }

    private DataStore(Context context){
        mData = new ArrayList<>();

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mNumTimesRun = settings.getInt(KEY_NUM_TIMES_RUN, 0);

        String arrayListAsJson = settings.getString(KEY_ITEMS_STRING, "[]");
        Gson gson = new Gson();
        ArrayList<Meal> array = gson.fromJson(arrayListAsJson, new TypeToken<ArrayList<Meal>>(){}.getType());
        Log.d("DataStore", "reading string - arrayListToJson" + arrayListAsJson);
        mData = array;

    }

    public boolean commitChanges(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_NUM_TIMES_RUN, mNumTimesRun);

        Gson gson = new GsonBuilder().create();

        String arrayListToJson = gson.toJson(mData);
        Log.d("DataStore", "Saving string - arrayListToJson=" + arrayListToJson);

        editor.putString(KEY_ITEMS_STRING, arrayListToJson);

        boolean success = editor.commit();
        return success;
    }

    public int getNumTimesRun() {
        return mNumTimesRun;
    }

    public void setNumTimesRun(int numTimesRun) {
        mNumTimesRun = numTimesRun;
    }

    public ArrayList<Meal> getData() {
        return mData;
    }
}
