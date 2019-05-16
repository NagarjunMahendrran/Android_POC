package com.example.technicolor.technicolorthomson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> device_name;
    public static ArrayList<String> device_mac;
    public static ArrayList<String> device_state;
    public static String firewallmode;
    public static String alexa;
    public static String smart;
    public static String cujoa;
    public static String wifiDr;
    public static String wifi_state;
    public static String internet;
    public static JSONObject internet_access;
    static EditText email_id_input;
    static EditText password_input;
    static String regex = "^(.+)@(.+)$";
    static Pattern pattern ;
    static Matcher match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_id_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        pattern = Pattern.compile(regex);
    }

    public void onClick(View view) {
        Button bt = (Button) view;
        if(bt.getId() == R.id.sign_up){
            Intent myIntent = new Intent(this, Registration.class);
            startActivity(myIntent);
        }
        if(bt.getId() == R.id.sign_in){
            if(validation()){
            }else{
                Log.e("I am not okay", "I am not okay");
            }

        }

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }

    public boolean validation() {
        match = pattern.matcher(email_id_input.getText());
        if (TextUtils.isEmpty(email_id_input.getText())) {
            email_id_input.setError("Email-ID/UserName should not be Empty");
            email_id_input.setBackgroundResource(R.drawable.error_border);
            return false;
        }else if(!match.matches()){
            email_id_input.setError("Please enter valid Email-ID");
            email_id_input.setBackgroundResource(R.drawable.error_border);
            return false;
        }else {
            email_id_input.setError(null);
            email_id_input.clearFocus();
            email_id_input.setBackgroundResource(R.drawable.success_border);
        }
        if (TextUtils.isEmpty(password_input.getText())) {
            password_input.setError("Password should not be Empty");
            password_input.setBackgroundResource(R.drawable.error_border);
            return false;
        }
        return true;
    }

    public void ack(String error, Context c) {

        if (error.equals("success")) {
            Toast.makeText(c, Html.fromHtml("<p><strong><font color='black'>Changes Done Successfully</font></strong></p>"),
                    Toast.LENGTH_SHORT).show();
        } else if (error.equals("sync")) {
            Toast.makeText(c, Html.fromHtml("<p><strong><font color='black'> Synced Successfully</font></strong></p>"),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, Html.fromHtml("<p><strong><font color='red'>Operation Failed</font></strong></p>"),
                    Toast.LENGTH_SHORT).show();
        }

    }

}
