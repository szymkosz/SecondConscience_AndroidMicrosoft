package com.secondconscience.backend;

import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;

public class Driver {
    // Threshold score used for analysis
    final static float sentimentThreshold = (float) 0.50;
    final static float minLUISMatchScore = (float) 0.70;

    public static boolean analyze(String message, ExecutionContext context) {
        Documents docs = new Documents();
        docs.add("1", "en", message);

        float sentimentScore = 1;

        try {
            String ret = GetSentiment.GetSentiment(docs);
            ret = GetSentiment.prettify(ret);
            sentimentScore = findScore(ret, context);

            context.getLogger().info("sentimentScore: " + String.valueOf(sentimentScore));

            if (sentimentScore < sentimentThreshold) {
                try {

                    String LUISResponse = LuisGetRequest.sendGet(message);
                    String topIntent = findTopIntent(LUISResponse, context);

                    float LUISScore = findScore(LUISResponse, context);

                    context.getLogger().info("topIntent: " + topIntent);
                    context.getLogger().info("LUIS Score: " + String.valueOf(LUISScore));

                    if (LUISScore > minLUISMatchScore && !topIntent.equals("None"))
                    {
                        // Bullying is detected
                        context.getLogger().info("Cyber-bullying has been detected.");
                        return true;
                    }
                } catch (Exception e) {

                    context.getLogger().info("Caught an Exception");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            context.getLogger().info("Caught an Exception");
            e.printStackTrace();
        }
        context.getLogger().info("No cyber-bullying detected.");
        return false;
    }


    /**
     * Extracts the top score from the API response
     *
     * @param input - String response from LUIS or Azure
     * @return numFinal - float of the score
     */
    public static float findScore(String input, ExecutionContext context) {
        // Logging on the server
        context.getLogger().info("findScore input: ");
        context.getLogger().info(input);

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
    public static String findTopIntent(String input, ExecutionContext context) {
        // Logging on the server
        context.getLogger().info("findTopIntent input: ");
        context.getLogger().info(input);

        int intentIdxStart = input.indexOf("intent") + 10;
        int intentIdxEnd = input.indexOf("\"", intentIdxStart);
        return input.substring(intentIdxStart, intentIdxEnd);
    }
}
