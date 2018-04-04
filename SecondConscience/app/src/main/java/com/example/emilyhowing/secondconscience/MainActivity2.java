//package com.example.emilyhowing.secondconscience;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//
//import static com.example.emilyhowing.secondconscience.EnterMessage.message;
//
//public class MainActivity implements AsyncResponse {
//
//
//    SentimentAsyncTask asyncTask = new SentimentAsyncTask();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        //this to set delegate/listener back to this class
//        asyncTask.delegate = this;
//
//        //execute the async task
//        asyncTask.execute();
//    }
//
//    //this override the implemented method from asyncTask
//
//    // Output is what is passed to OnPostExecute from doInBackground
//    @Override
//    void processFinish(String output){
//
//        //Here you will receive the result fired from async class
//        //of onPostExecute(result) method.
//    }
//
//}
