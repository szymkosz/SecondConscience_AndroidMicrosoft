package com.example.emilyhowing.secondconscience;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import static com.example.emilyhowing.secondconscience.EnterMessage.Documents.add;
//import static com.example.emilyhowing.secondconscience.EnterMessage.Documents.prettify;
//import static com.example.emilyhowing.secondconscience.EnterMessage.GetSentiment.accessKey;
//import static com.example.emilyhowing.secondconscience.EnterMessage.GetSentiment.host;
//import static com.example.emilyhowing.secondconscience.EnterMessage.GetSentiment.path;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    public static boolean isBullying = false;
    private static boolean doneProcessing = false;

    public static String message = "";
    public static String rStr = "";
    public static float score = 1;

    @Override
    public void processFinish(String output){

        if (output.equals("true"))
        {
            Log.d("OPEOutput", output);
            isBullying = true;
            Log.d("isBullyingProcFin", Boolean.toString(isBullying));
            doneProcessing = true;
        }

        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

    }

    SentimentAsyncTask asyncTask = new SentimentAsyncTask();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this to set delegate/listener back to this class

        asyncTask.delegate = this;

        isBullying = false; // Reset back to false for next analysis
        doneProcessing = false;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_message);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        EditText e = (EditText) (findViewById(R.id.editText));
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    fab.performClick();
                }
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       EditText e = (EditText) (findViewById(R.id.editText));
                                       message = e.getText().toString();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                                       //TextView tv = (TextView) findViewById(tv);
                                       //tv.setText(Float.toString(score));
//                                       new SentimentAsyncTask().execute(message);

                                       //// TODO: 3/28/18 make notification
                                       Log.d("isBullyingBeforeWhile", Boolean.toString(isBullying));
                                       Log.d("doneProcessingBeforWhil", Boolean.toString(doneProcessing));
                                       Log.d("AsyncTaskStatus", asyncTask.getStatus().toString());


                                       asyncTask.execute(message);
                                       //new SentimentAsyncTask().execute(message);
                                       try {
                                           Thread.sleep(700);
                                       } catch (Exception ex) {
                                           ex.printStackTrace();
                                       }

                                       Log.d("AsyncTaskPostWait", asyncTask.getStatus().toString());
                                       Log.d("Timer", "Out of delay handler");

                                        //just added
                                       ///

//                                       while (SentimentAsyncTask.Status.RUNNING == asyncTask.getStatus() || SentimentAsyncTask.Status.PENDING == asyncTask.getStatus()) {
//                                           //Log.d("inWhile", "STUCK IN THE WHILE LOOP");
//                                           Log.d("isBullyingInWhile", Boolean.toString(isBullying));
//
//                                       }
                                           Log.d("isBullyingBeforeAlert", Boolean.toString(isBullying));

                                           if (isBullying) {
                                               Log.d("Bully: ", "Yes, this bitch is bullying");
                                               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                               builder
                                                       .setTitle("Bullying Detected")
                                                       .setMessage("This post has been found to contain cyber bullying sentiment. Would you still like to post it?")
                                                       .setIcon(android.R.drawable.ic_dialog_alert)
                                                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               //Yes button clicked, do something
                                                               Toast.makeText(MainActivity.this, "User Reported",
                                                                       Toast.LENGTH_LONG).show();
                                                           }
                                                       })
                                                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               //Yes button clicked, do something
                                                               Toast.makeText(MainActivity.this, "Notification Ignored",
                                                                       Toast.LENGTH_LONG).show();
                                                           }
                                                       })                        //Do nothing on no
                                                       .show();
                                           }

                                       AsyncResponse temp = asyncTask.delegate;
                                       asyncTask = new SentimentAsyncTask();
                                       asyncTask.delegate = temp;
                                   }
        });
    }
}

        // Replace or verify the region.

        // You must use the same region in your REST API call as you used to obtain your access keys.
        // For example, if you obtained your access keys from the westus region, replace
        // "westcentralus" in the URI below with "westus".

        // NOTE: Free trial access keys are generated in the westcentralus region, so if you are using
        // a free trial access key, you should not need to change this region.
         //static String host = "https://westus.api.cognitive.microsoft.com";

         //static String path = "/text/analytics/v2.0/sentiment";
