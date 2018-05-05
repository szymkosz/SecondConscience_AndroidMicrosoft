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

    // Member variables
    public static boolean isBullying = false;
    SentimentAsyncTask asyncTask = new SentimentAsyncTask();
    public static String message = "";

    /**
     * Implementation of the processFinish method declared in AsyncResponse.
     * <p>
     * By being implemented in the MainActivity, this method can modify MainActivity's
     * variables, acting as an interface between the MainActivity thread and the
     * AsyncTask thread.
     *
     * @param output - boolean representation of the response from the message analysis.
     *               can be "true" or "false".
     */
    @Override
    public void processFinish(String output) {

        if (output.equals("true"))
        {
            isBullying = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set delegate/listener back to this class
        asyncTask.delegate = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_message);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Create text box for user input
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

                // Reset flag back to false before this analysis
                isBullying = false;

                asyncTask.execute(message);

                // Sleep this thread so it does not continue on before analysis is complete
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

                // Make new instance of custom AsyncTask (since it can only be executed once)
                AsyncResponse temp = asyncTask.delegate;
                asyncTask = new SentimentAsyncTask();
                asyncTask.delegate = temp;

            } // End onClick()
        }); // Close setOnClickListener()
    } // End onCreate
}//end class
