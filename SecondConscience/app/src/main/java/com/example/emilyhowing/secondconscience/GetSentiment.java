package com.example.emilyhowing.secondconscience;

/**
 * Created by emilyhowing on 3/28/18.
 * Code is from Microsoft Azure website: https://docs.microsoft.com/en-us/azure/cognitive-services/text-analytics/quickstarts/java
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

/*
 * Gson: https://github.com/google/gson
 * Maven info:
 *     groupId: com.google.code.gson
 *     artifactId: gson
 *     version: 2.8.1
 *
 * Once you have compiled or downloaded gson-2.8.1.jar, assuming you have placed it in the
 * same folder as this file (GetSentiment.java), you can compile and run this program at
 * the command line as follows.
 *
 * javac GetSentiment.java -classpath .;gson-2.8.1.jar -encoding UTF-8
 * java -cp .;gson-2.8.1.jar GetSentiment
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetSentiment {

    static String accessKey = "2714f483344d4415a6e530fd9fe63a33";

    static String host = "https://westus.api.cognitive.microsoft.com";

    static String path = "/text/analytics/v2.0/sentiment";

    public static String GetSentiment(Documents documents) throws Exception {
        String text = new Gson().toJson(documents);
        byte[] encoded_text = text.getBytes("UTF-8");

        URL url = new URL(host + path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(encoded_text, 0, encoded_text.length);
        wr.flush();
        wr.close();

        StringBuilder response = new StringBuilder();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();
    }

    /**
     *
     */
    public static String prettify(String json_text) {
        Log.d("TAG2:", json_text);
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    /**
     *
     */
    static class Document {
        public String id, language, text;

        public Document(String id, String language, String text) {
            this.id = id;
            this.language = language;
            this.text = text;
        }
    }

    /**
     *
     */
    static class Documents {
        public List<Document> documents;

        public Documents() {
            this.documents = new ArrayList<Document>();
        }

        public void add(String id, String language, String text) {
            this.documents.add(new Document(id, language, text));
        }
    }
}
