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


public class MainActivity extends AppCompatActivity implements AsyncResponse {

    public static boolean isBullying = false;
    private static boolean doneProcessing = false;

    public static String message = "";

    @Override
    public void processFinish(String output){

        if (output.equals("true"))
        {
            isBullying = true;
            doneProcessing = true;
        }
    }

    SentimentAsyncTask asyncTask = new SentimentAsyncTask();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this to set delegate/listener back to this class
        asyncTask.delegate = this;

        //Reset back to false for next analysis
        isBullying = false;
        doneProcessing = false;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_message);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        EditText e = (EditText) (findViewById(R.id.editText));
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
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

                                       isBullying = false;

                                       asyncTask.execute(message);
                                       try {
                                           Thread.sleep(700);
                                       } catch (Exception ex) {
                                           ex.printStackTrace();
                                       }

                                           Log.d("isBullyingBeforeAlert", Boolean.toString(isBullying));

                                           if (isBullying) {
                                               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                               builder
                                                       .setTitle("Bullying Detected")
                                                       .setMessage("This post has been found to contain cyber bullying sentiment. Would you still like to post it?")
                                                       .setIcon(android.R.drawable.ic_dialog_alert)
                                                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               //Yes button clicked, do something
                                                               Toast.makeText(MainActivity.this, "Posting...",
                                                                       Toast.LENGTH_LONG).show();
                                                           }
                                                       })
                                                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               //Yes button clicked, do something
                                                               Toast.makeText(MainActivity.this, "Post deleted.",
                                                                       Toast.LENGTH_LONG).show();

                                                           }
                                                       })                        //Do nothing on no
                                                       .show();
                                           }

                                       // Reset all relevant fields/vars
                                       e.setText("");
                                       Log.d("PENIS", Boolean.toString(isBullying));
                                       AsyncResponse temp = asyncTask.delegate;
                                       asyncTask = new SentimentAsyncTask();
                                       asyncTask.delegate = temp;
                                   }
        });
    }
}
