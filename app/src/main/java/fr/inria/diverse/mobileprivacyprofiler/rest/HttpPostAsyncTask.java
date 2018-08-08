package fr.inria.diverse.mobileprivacyprofiler.rest;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpPostAsyncTask extends AsyncTask<String, Void , Message > {

    public static final int HTT_STATUS_CODE = 404;
    private static final String TAG = HttpPostAsyncTask.class.getSimpleName();
    // This is the JSON body of the post
    private String postData;
    private Handler handler;

    // This is a constructor that allows you to pass in the JSON body
    public HttpPostAsyncTask(String postData, Handler handler) {
        if (postData != null) {
            this.postData = postData;
            this.handler = handler;
        }
    }

    // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
    @Override
    protected Message doInBackground(String... params) {

        Message handlerMessage = new Message();
        handlerMessage.what = HTT_STATUS_CODE;

        try {
            // This is getting the url from the string we passed in
            URL url = new URL(params[0]);
            // Create the urlConnection
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();


            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestMethod("POST");

            // Send the post body
            if (this.postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData);
                writer.flush();
            }

            int statusCode = urlConnection.getResponseCode();

            Message httpResponse = new Message();
            httpResponse.what = statusCode;

            InputStream inputStream;
            if(statusCode == 200 || statusCode == 201)
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            else
                inputStream = new BufferedInputStream(urlConnection.getErrorStream());


            httpResponse.obj = convertInputStreamToString(inputStream);
            handlerMessage.obj = httpResponse;

            return handlerMessage;

        } catch (IOException e) {
            e.printStackTrace();
            handlerMessage.obj = null;
            return handlerMessage;
        }
    }

    @Override
    protected void onPostExecute(Message message) {
        if (handler != null && message != null) {
            handler.sendMessage(message);
        }
    }

    public static String convertInputStreamToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
