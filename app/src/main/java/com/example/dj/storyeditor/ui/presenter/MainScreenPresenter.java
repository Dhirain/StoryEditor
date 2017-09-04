package com.example.dj.storyeditor.ui.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.dj.storyeditor.fileUtils.ReadWrite;
import com.example.dj.storyeditor.permision.PermissionProviderImpl;
import com.example.dj.storyeditor.utills.Constants;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DJ on 31-08-2017.
 */

public class MainScreenPresenter {
    private static final String TAG = "MainScreenPresenter";
    private Context context;
    private String currentFileName;

    public MainScreenPresenter(Context context) {
        this.context = context;
    }

    public void onViewAttached() {
        checkPermision();
        setCurrentFileName();
    }

    private void setCurrentFileName() {
        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        currentFileName = prefs.getString(Constants.CURRENT_FILE_PREFS, null);
        if(currentFileName == null ){
            currentFileName = Constants.DEFAULT_FILE_NAME;
        }
    }

    public void checkPermision(){
        PermissionProviderImpl provider = new PermissionProviderImpl(context);
        if(!provider.hasWriteExternalStoragePermission()){
            provider.requestWriteExternalStoragePermission();
        }

    }

    public void autoSave(String data) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.PREV_DATA, data);
        editor.apply();
        try {
            ReadWrite.saveFile(data, currentFileName);
            Toast.makeText(context, "Auto Save!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveFileAs(String data, String FileName){
        currentFileName = FileName;
        if(!currentFileName.endsWith(Constants.XML)){
            currentFileName = FileName + Constants.XML;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(Constants.CURRENT_FILE_PREFS, currentFileName);
        editor.apply();
        try {
            ReadWrite.saveFile(data, currentFileName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getCurrentFileName() {
        return currentFileName;
    }

    public String getPrevData() {
        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.PREV_DATA, null);
    }

    public String getDataFromFile(String FilePath, String lastPathSegment) {
        String data = "";
        try {
            data = ReadWrite.readFile(FilePath);
            currentFileName = lastPathSegment;
            Log.d(TAG, "handleIntent: data from file"+data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
