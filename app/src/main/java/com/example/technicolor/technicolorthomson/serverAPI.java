package com.example.technicolor.technicolorthomson;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;


public class serverAPI extends AsyncTask<String, Void, String> {

    static String state_;
    static AlertDialog.Builder alertDialogBuilder;
    static AlertDialog alertDialog;
    static JSONObject speech;
    InputStream inputStream;
    JSONObject query;
    String temp;
    Context context;
    String ResponseData;

    @SuppressLint("ResourceType")
    public serverAPI(String query, Context c) {

        temp = query;
        JSONObject jsonObject = null;

        try {

            // if(query.equals("InternetAccess")) {
            //    state_ = MainActivity.internet_access.has(single_user.mac_address) ?  MainActivity.internet_access.getString(single_user.mac_address).equals("allow") ? "block" : "allow" : "block";
            //    jsonObject = new JSONObject("{\"session\": {\"new\": false,\"sessionId\": \"amzn1.echo-api.session.d97d9658-900e-463d-8bad-446f5710faca\"},\"request\": {\"type\": \"IntentRequest\",\"requestId\": \"amzn1.echo-api.request.e6e7b8e2-a143-4a63-9d6a-5bc7e6cc869d\",\"intent\": {\"device\": {\"mac\": '"+single_user.mac_address+"',\"state\": '"+state_+"'},\"name\":" + query + ",\"slots\": {\"thing\": {\"name\": \"thing\",\"value\": \"John\"}}}}}");

            //   }else if(query.equals("Subscribe")) {
            // jsonObject = new JSONObject("{\"session\": {\"new\": false,\"sessionId\": \"amzn1.echo-api.session.d97d9658-900e-463d-8bad-446f5710faca\"},\"request\": {\"type\": \"IntentRequest\",\"requestId\": \"amzn1.echo-api.request.e6e7b8e2-a143-4a63-9d6a-5bc7e6cc869d\",\"intent\": {\"app\": {\"appname\": '"+apps.selected_button+"'},\"name\":" + query + ",\"slots\": {\"thing\": {\"name\": \"thing\",\"value\": \"John\"}}}}}");

            // }
            jsonObject = new JSONObject("{\"session\": {\"new\": false,\"sessionId\": \"amzn1.echo-api.session.d97d9658-900e-463d-8bad-446f5710faca\"},\"request\": {\"type\": \"IntentRequest\",\"requestId\": \"amzn1.echo-api.request.e6e7b8e2-a143-4a63-9d6a-5bc7e6cc869d\",\"intent\": {\"name\":" + query + ",\"slots\": {\"thing\": {\"name\": \"thing\",\"value\": \"John\"}}}}}");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.query = jsonObject;
        this.context = c;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Loading...");
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static void _switch() {
        alertDialog.hide();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {

        /* forming th java.net.URL object */
        URL url = null;
        try {
            url = new URL(" https://ey06ub3wba.execute-api.us-west-2.amazonaws.com/default");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /* pass post data */
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            osw.write(String.valueOf(query));
            osw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /* Get Response and execute WebService request*/
        int statusCode = 0;
        try {
            statusCode = urlConnection.getResponseCode();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /* 200 represents HTTP OK */
        if (statusCode == HttpsURLConnection.HTTP_OK) {

            try {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

            } catch (IOException e) {
                _switch();
                Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            ResponseData = convertStreamToString(inputStream);


        } else {

            ResponseData = null;
        }

        return ResponseData;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onPostExecute(String result) {
        MainActivity ma = new MainActivity();
        JSONObject jobj = null;
        if (result != null) {

            try {
                jobj = new JSONObject(result);
            } catch (JSONException e) {
                Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            if (temp.equals("Registration")) {
                Registration re = new Registration();

                try {
                    speech = jobj.getJSONObject("response");
                    speech = speech.getJSONObject("outputSpeech");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (speech.get("text").equals("message :Email id already exist , result :Failed")) {
                        Registration.email_id.setError("Email Id already existing");
                        Registration.email_id.setText("");
                        Registration.email_id.setBackgroundResource(R.drawable.error_border);
                        re.createsnackbar(2);
                    }
                    if (speech.get("text").equals("message :Failed to add , result :Failed")) {
                        re.createsnackbar(3);
                    }
                    if (speech.get("text").equals("message :Added to database , result :Success")) {
                        re.createsnackbar(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            /*
            if (temp.equals("getPageLoadinfo")) {

                try {
                    int collection = 0;
                    JSONObject temp = jobj.getJSONObject("getDeviceInfo");
                    if(temp != null){
                        int end = temp.length() / 3;
                        for (int i = 1; collection < end; i++) {
                            if (temp.has("rpc.hosts.host." + i + ".MACAddress")) {
                                MainActivity.device_name.add(collection, temp.getString("rpc.hosts.host." + i + ".FriendlyName"));
                                MainActivity.device_mac.add(collection, temp.getString("rpc.hosts.host." + i + ".MACAddress"));
                                MainActivity.device_state.add(collection, temp.getString("rpc.hosts.host." + i + ".State"));
                                collection = collection + 1;
                            }
                        }
                    }
                    temp = jobj.getJSONObject("Firewallmode");

                    if(temp != null) {
                        MainActivity.firewallmode = temp.getString("rpc.network.firewall.mode");
                    }

                    if(jobj.has("Alexa")) {
                        MainActivity.alexa = jobj.getString("Alexa");
                    }

                    if(jobj.has("smartthing")) {
                        MainActivity.smart = jobj.getString("smartthing");
                    }

                    if(jobj.has("cujo")) {
                        MainActivity.cujoa = jobj.getString("cujo");
                    }

                    if(jobj.has("wifiDr")) {
                        MainActivity.wifiDr = jobj.getString("wifiDr");
                    }

                    temp = jobj.getJSONObject("WiFi");
                    if(temp != null) {
                        MainActivity.wifi_state = temp.getString("uci.wireless.wifi-iface.@wl0_1.state");
                    }
                    if(jobj.has("Internet")) {
                        MainActivity.internet = jobj.getString("Internet");
                    }
                    if(jobj.has("InternetAccess")) {
                        MainActivity.internet_access = jobj.getJSONObject("InternetAccess");
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                ma.ack("sync",context);
              //  ma._update();
            }
            if (temp.equals("WiFiEnable") || temp.equals("WiFiDisable") ) {
                try {
                    speech = jobj.getJSONObject("response");
                    speech = speech.getJSONObject("outputSpeech");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Log.e("testing", String.valueOf(speech.get("text").equals("Technicolor's HomeGateway WiFi  Guest Network is ON")));
                    if(temp.equals("WiFiEnable") && speech.get("text").equals("Technicolor's HomeGateway WiFi  Guest Network is ON")) {
                        MainActivity.wifi_state = "1";
                        _switch();
                        ma.ack("success",context);
                    }else  if(temp.equals("WiFiDisable") && speech.get("text").equals("Technicolor's HomeGateway WiFi  Guest Network is OFF")) {
                        MainActivity.wifi_state = "0";
                        _switch();
                        ma.ack("success",context);
                    }else {
                        //MainActivity.change_old_wifi(temp);
                        _switch();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (temp.equals("FirewallmodeHigh")) {
                MainActivity.firewallmode = "high";
                _switch();

            }
            if (temp.equals("FirewallmodeLow")) {
                MainActivity.firewallmode = "lax";
                _switch();
                ma.ack("success",context);
            }
            if (temp.equals("FirewallmodeNormal")) {
                MainActivity.firewallmode = "normal";
                _switch();
                ma.ack("success",context);
            }
          /*  if(temp.equals("InternetAccess")){

                try {
                    if( jobj.get("InternetAccess") instanceof JSONArray){

                      //  single_user.toggle_old_state(jobj.getJSONObject("InternetAccess").getString(single_user.mac_address));
                        _switch();
                    }
                    else if(jobj.getJSONObject("InternetAccess").getString(single_user.mac_address).equals(state_)){

                        if(state_.equals("allow")) {

                            MainActivity.internet_access.remove(single_user.mac_address);
                        }
                        else {
                            try {
                                MainActivity.internet_access.put(single_user.mac_address, "block");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        _switch();
                        ma.ack("success",context);

                    }else {
                        single_user.toggle_old_state(jobj.getJSONObject("InternetAccess").getString(single_user.mac_address));
                        _switch();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(temp.equals("InternetEnable") || temp.equals("InternetDisable") ){
                try {
                    if(jobj.getString("Internet").equals(temp)){

                        if(temp.equals("InternetEnable")){
                            MainActivity.internet = "InternetEnable";
                        }else {
                            MainActivity.internet = "InternetDisable";
                        }
                        _switch();
                        ma.ack("success",context);
                    }else{
                        user_list.change_old_state(jobj.getString("Internet"));
                        _switch();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(temp.equals("Subscribe")){
                try {
                    jobj = new JSONObject(result);
                } catch (JSONException e) {
                    Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                if(apps.selected_button.equals("Alexa")){
                    try {
                        if(jobj.getString("Alexa").equals("Subscribe")) {
                            MainActivity.alexa = "subscribe";
                            apps.change_buttonn_state();
                        }else {
                            Toast.makeText(apps.apps_page_main, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                                    Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(apps.selected_button.equals("wifiDr")){
                    try {
                        if(jobj.getString("wifiDr").equals("Subscribe")) {
                            MainActivity.alexa = "subscribe";
                            apps.change_buttonn_state();
                        }else {
                            Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                                    Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity.wifiDr = "Subscribe";
                }
                if(apps.selected_button.equals("smartthing")){
                    try {
                        if(jobj.getString("smartthing").equals("Subscribe")) {
                            MainActivity.smart = "Subscribe";
                            apps.change_buttonn_state();
                        }else {
                            Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                                    Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if(apps.selected_button.equals("cujo")){
                    try {
                        if(jobj.getString("cujo").equals("Subscribe")) {
                            MainActivity.cujoa = "Subscribe";
                            apps.change_buttonn_state();
                        }else {
                            Toast.makeText(context, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                                    Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                _switch();
            } */

        } else {
            ma.ack("error", context);
            _switch();

        }

    }
}
