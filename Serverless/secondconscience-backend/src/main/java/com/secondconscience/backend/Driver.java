public class Driver {
    // Threshold score used for analysis
    final float sentimentThreshold = (float) 0.20;
    final float minLUISMatchScore = (float) 0.70;

    public static boolean analyze(String message) {
        Documents docs = new Documents();
        docs.add("1", "en", message);

        float score = 1;

        try {
            String ret = GetSentiment.GetSentiment(docs);
            ret = GetSentiment.prettify(ret);
            score = findScore(ret);

            if (score < sentimentThreshold) {
                try {

                    String LUISResponse = LuisGetRequest.sendGet(message);
                    String topIntent = findTopIntent(LUISResponse);

                    float newScore = findScore(LUISResponse);
                    if (newScore > minLUISMatchScore && !topIntent.equals("None"))
                    {
                        // Bullying is detected
                        return true
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false
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
