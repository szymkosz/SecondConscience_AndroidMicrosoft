package com.example.emilyhowing.secondconscience;

import android.os.AsyncTask;
import android.util.Log;

/**
 *
 * This class implements the custom AsyncTask created for this app.
 *  It is customized to perform all of the HTTP requests required for analysis
 *  and returns the result back to MainActivity by way of AsyncResponse, whose
 *  processFinish method gets called from this class, but executes in MainActivity
 *  to effect the necessary changes to communicate the decision made by the analysis
 *
 * The message for analysis should be passed as a string into the AsyncTask call to execute()
 * i.e. if asyncTask is a SentimentAsyncTask object, then it should be called as follows:
 *          asyncTask.execute(stringMessage)
 *
 */
public class SentimentAsyncTask extends AsyncTask<String, Void, String> {

    // Used to interface with the MainActivity thread.
    public AsyncResponse delegate = null;

    // Threshold score used for analysis
    final float sentimentThreshold = (float) 0.20;
    final float minLUISMatchScore = (float) 0.70;

    /**
     *
     * The majority of the work is done in this function, which connects separately
     *  to Text Analytics for sentiment analysis and to LUIS for classification
     *
     * @param strings - message string to be analyzed
     * @return string representation of the analysis decision. i.e. "true" or "false"
     */
    protected String doInBackground(String... strings) {
        String message = strings[0];

        HttpUrlConnect obj = new HttpUrlConnect();
        GetSentiment.Documents docs = new GetSentiment.Documents();
        docs.add("1", "en", message);
        Log.d("Input Message: ", message);

        float score = 1;

        try {

            String ret = GetSentiment.GetSentiment(docs);
            Log.d("GetSentiment Response: ", ret);
            ret = GetSentiment.prettify(ret);
            score = findScore(ret);
            Log.d("Sentiment Score: ", Float.toString(score));

            if (score < sentimentThreshold) {
                try {

                    String LUISResponse = LuisGetRequest.sendGet(message);
                    String topIntent = findTopIntent(LUISResponse);
                    float newScore = findScore(LUISResponse);
                    Log.d("LUIS Response", LUISResponse);
                    Log.d("LUIS topIntent: ", topIntent);
                    Log.d("LUIS Score: ", Float.toString(newScore));

                    // If the match exceeds the threshold and the topIntent is not "None"
                    if (newScore > minLUISMatchScore && !topIntent.equals("None")) {
                        Log.d("DETECTED: ", "Bullying has been detected");
                        MainActivity.isBullying = true;
                        return "true";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("NOT DETECTED: ", "Returning false");
        return "false";
    }

    protected void onPostExecute(String result) {
        Log.d("onPostExecute1: ", result);
        delegate.processFinish(result);
        Log.d("onPostExecute2: ", result);
    }

    /**
     * Extracts the top score from the API response
     *
     * @param input - String response from LUIS or Azure
     * @return numFinal - float of the score
     */
    public static float findScore(String input) {
        int scoreIndexStart = input.indexOf("score") + 8;
        int idx = scoreIndexStart + 2;
        while (Character.isDigit(input.charAt(idx))) {
            idx++;
        }
        int scoreIndexEnd = idx;
        String num = input.substring(scoreIndexStart, scoreIndexEnd); //get num
        num = num.replaceAll("\\s", ""); //remove whitespace
        float numFinal = Float.parseFloat(num); //cast to float
        return numFinal;
    }

    /**
     * Extracts the top intent from LUIS' Response
     *
     * @param input - String response from LUIS
     * @return String of the top intent
     */
    public static String findTopIntent(String input) {
        int intentIdxStart = input.indexOf("intent") + 10;
        int intentIdxEnd = input.indexOf("\"", intentIdxStart);
        return input.substring(intentIdxStart, intentIdxEnd);
    }
}
