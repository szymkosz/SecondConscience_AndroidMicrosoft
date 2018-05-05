package com.example.emilyhowing.secondconscience;

/**
 *
 * Used to interface between the AsyncTask thread and the MainActivity
 *
 *  In its OnPostExecute method, AsyncTask calls the processFinish method
 *  through a member variable called delegate, which points to the AsyncResponse
 *  instance. processFinish is implemented in MainActivity so that the analysis
 *  result can be passed back to the MainActivity.
 *
 */
public interface AsyncResponse {
    void processFinish(String output);
}
