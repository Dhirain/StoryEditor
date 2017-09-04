package com.example.dj.storyeditor.fileUtils;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.dj.storyeditor.utills.Constants;
import com.example.dj.storyeditor.application.StoryEditor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by DJ on 03-09-2017.
 */

public class ReadWrite {

    public static void saveFile(String data, String fileName) throws IOException{
        if(FileUtil.isExternalStorageWritable() && FileUtil.isExternalStorageReadable()){
            File file = new File(getAppDirectory(), fileName);
            Log.d("Write", "saveFile: "+file.getAbsolutePath());
            Log.d("Write", "saveFile: "+file.toString());
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(data.getBytes());
            }
            catch (Exception e){
                e.printStackTrace();
            }finally {
                stream.close();
            }
        }
        else {
            Toast.makeText(StoryEditor.singleton().getContext(), "SD card not found", Toast.LENGTH_SHORT).show();
        }
    }

    public static String readFile(String fileName) throws IOException{
        String myData = "";
        if(FileUtil.isExternalStorageWritable() && FileUtil.isExternalStorageReadable()){
            try {
                File file = new File(fileName);
                FileInputStream fis = new FileInputStream(file);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    myData = myData + strLine;
                }
                in.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(StoryEditor.singleton().getContext(), "SD card not found", Toast.LENGTH_SHORT).show();
        }
        return myData;
    }

    private static File createDirIfNotExists() {
        File folder = new File(Environment.getExternalStorageDirectory(), Constants.FOLDER_NAME);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    public static File getAppDirectory(){

        String root = Environment.getExternalStorageDirectory().toString();

        File myDir = new File(root + Constants.FOLDER_NAME);

        myDir.mkdirs();

        return myDir;

    }

}
