package com.lasys.app.quotes.smsintegra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS
{
    public String sendSms() {
        try {
            // Construct data


            //String apiKey = "apikey=" + "yourapiKey";
            String apiKey = "apikey=" + "gc/qMShtg6g-dWXg1PHZXo6tEI4G1nS8xwPtCDEdGT";
            String message = "&message=" + "Hello VSA SMS Testing";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "+918309520838";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}
