package com.zen.mdp_lab3;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AsyncDemoActivity extends AppCompatActivity {

    private TextView mytextView;
    private Button pressMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mytextView = (TextView) findViewById(R.id.myTextView);
        pressMe = (Button) findViewById(R.id.pressMe);

        pressMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
    }

    public void buttonClicked(View view) {
        AsyncTask task = new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        // this is called before the background task which can be used to perform initialization step
        protected void onPreExecute() {
        }

        @Override
        /*
        This method does not have access
        to the main thread so cannot make user interface changes
        */
        protected String doInBackground(String... strings) {
            int i = 0;
            while (i <= 20) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                    i++;
                }
                catch (Exception e) {
                    return (e.getLocalizedMessage());
                }
            }
            return "Button finally pressed";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mytextView.setText("Counter = " + values[0]);
        }

        @Override
        /*
        called when the tasks performed within the
        doInBackground() method complete
        */
        protected void onPostExecute(String result) {
            mytextView.setText(result);
        }
    }
}
