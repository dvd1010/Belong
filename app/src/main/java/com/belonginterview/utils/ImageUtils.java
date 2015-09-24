package com.belonginterview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by SuperProfs on 23/09/15.
 */
public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();

    private ImageUtils() {

    }

    public static Bitmap getBitmapFromUrl(String url) {
   /*     ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        executorService.shutdown();*/
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((new URL(url)).openConnection().getInputStream());
        } catch (Exception e) {
            Log.e(TAG, "unable to generate bitmap from url", e);
        }
        return bitmap;
    }
}
