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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


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

        try {
            //We need that because for now the server use a self-signed certificate.
            ignoreCertificate();
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

            Message handlerMessage = new Message();
            Message bodyMessage = new Message();

            handlerMessage.what = HTT_STATUS_CODE;
            bodyMessage.what = statusCode;

            InputStream inputStream;
            if(statusCode == 200 || statusCode == 201)
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            else
                inputStream = new BufferedInputStream(urlConnection.getErrorStream());


            bodyMessage.obj = convertInputStreamToString(inputStream);
            handlerMessage.obj = bodyMessage;

            return handlerMessage;

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public static void ignoreCertificate(){
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

}
