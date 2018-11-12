package com.zen.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutionException;

public class MyService extends Service {
    public MyService() {
    }

    private static final String TAG = "ServiceExample";

    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        AsyncTask task = new SrvTask().executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, startId
        );
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    public void onDestoy() {
        Log.i(TAG, "Service onDestroy");
    }

    private class SrvTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer...params) {
            int startId = params[0];
            int i = 0;
            while (i <= 3) {
                publishProgress(params[0]);
                try {
                    Thread.sleep(100000);
                    i++;
                } catch (Exception e) {
                }
            }
            return ("Service complete" + startId);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Integer...values) {
            Log.i(TAG, "Service running" + values[0]);
        }

    }
}
