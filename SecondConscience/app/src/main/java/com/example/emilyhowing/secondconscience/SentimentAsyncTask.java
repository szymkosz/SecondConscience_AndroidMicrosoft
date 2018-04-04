package com.example.emilyhowing.secondconscience;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by emilyhowing on 4/2/18.
 * This class...
 */

public class SentimentAsyncTask extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;

    final float sentimentThreshold = (float) 0.20;
    final float minLUISMatchScore = (float) 0.80;

    protected String doInBackground(String... strings) {
        String message = strings[0];
        HttpUrlConnect obj = new HttpUrlConnect();
        GetSentiment.Documents docs = new GetSentiment.Documents();
        docs.add("1", "en", message);
        Log.d("Message: ", message);
        float score = 1;
        try {
            String ret = GetSentiment.GetSentiment(docs);
            Log.d("Sentiment Response: ", ret);
            ret = GetSentiment.prettify(ret);
            score = findScore(ret);
            Log.d("Score", Float.toString(score));

            if (score < sentimentThreshold) {
                try {
                    String LUISResponse = LuisGetRequest.sendGet(message);
                    String topIntent = findTopIntent(LUISResponse);
                    float newScore = findScore(LUISResponse);
                    Log.d("LUIS Response", LUISResponse);
                    Log.d("topIntent", topIntent);
                    Log.d("newScore", Float.toString(newScore));

                    if (newScore > minLUISMatchScore && !topIntent.equals("None")) {
                        Log.d("Bullying", "Bullying has been detected");
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
        Log.d("No bully", "Returning false");
        return "false";
    }

    protected void onPostExecute(String result) {

        Log.d("PExecute1", result);

        delegate.processFinish(result);
        Log.d("PExecute2", result);


    }

    /**
     * This function extracts the score from the Json
     * @param input in json
     * @return score
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
     * This function extracts the top intent from the json
     * @param input in json
     * @return String of the top intent
     */
    public static String findTopIntent(String input) {
        int intentIdxStart = input.indexOf("intent") + 10;
        int intentIdxEnd = input.indexOf("\"", intentIdxStart);
        return input.substring(intentIdxStart, intentIdxEnd);
    }
}