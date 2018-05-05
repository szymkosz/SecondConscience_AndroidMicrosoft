// package com.example.emilyhowing.secondconscience;
//
// import android.os.AsyncTask;
// import android.util.Log;
//
// import java.io.*;
// import java.net.*;
// import javax.net.ssl.HttpsURLConnection;
//
// /**
//  *
//  * This class implements the custom AsyncTask created for this app.
//  *  It is customized to perform all of the HTTP requests required for analysis
//  *  and returns the result back to MainActivity by way of AsyncResponse, whose
//  *  processFinish method gets called from this class, but executes in MainActivity
//  *  to effect the necessary changes to communicate the decision made by the analysis
//  *
//  * The message for analysis should be passed as a string into the AsyncTask call to execute()
//  * i.e. if asyncTask is a SentimentAsyncTask object, then it should be called as follows:
//  *          asyncTask.execute(stringMessage)
//  *
//  */
// public class SentimentAsyncTask extends AsyncTask<String, Void, String> {
//
//     public AsyncResponse delegate = null;
//
//     String host = "https://secondconscience-backend-20180504160033552.azurewebsites.net/api/bullying";
//
//     protected String doInBackground(String... strings) {
//         String message = strings[0];
//
//         try {
//             URL url = new URL(host);
//             HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//             connection.setRequestMethod("POST");
//             connection.setRequestProperty("Content-Type", "text/json");
//             connection.setDoOutput(true);
//
//             OutputStream out = new BufferedOutputStream(connection.getOutputStream());
//
//             String text = "\"text\": \"" + message + "\"";
//             byte[] encoded_text = text.getBytes("UTF-8");
//
//             out.write(encoded_text, 0, encoded_text.length);
//             out.flush();
//             out.close();
//
//             StringBuilder response = new StringBuilder();
//             BufferedReader in = new BufferedReader(new InputStreamReader(
//                     connection.getInputStream()));
//             String line;
//             while ((line = in.readLine()) != null) {
//                 response.append(line);
//             }
//             in.close();
//             Log.d("NEW API", response.toString());
//
//             if (response.toString().contains("true")) {
//                 Log.d("DETECTED: ", "Bullying has been detected");
//                         MainActivity.isBullying = true;
//                         return "true";
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//
//         Log.d("NOT DETECTED: ", "Returning false");
//         return "false";
//     }
//
//     protected void onPostExecute(String result) {
//         Log.d("onPostExecute1: ", result);
//         delegate.processFinish(result);
//         Log.d("onPostExecute2: ", result);
//     }
// }
