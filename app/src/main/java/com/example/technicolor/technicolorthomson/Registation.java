package com.example.technicolor.technicolorthomson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registation extends AppCompatActivity {
    static EditText username;
    static EditText email_id;
    static EditText password;
    static EditText serial_id;
    static String regex = "^(.+)@(.+)$";
    static Pattern pattern ;
    static Matcher match;
    static Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        username = findViewById(R.id.signup_input_name);
        email_id = findViewById(R.id.signup_input_email);
        password = findViewById(R.id.signup_input_password);
        serial_id = findViewById(R.id.serial_id_text);
        pattern = Pattern.compile(regex);
    }

    public void onClick(View view) {
        Button bt = (Button) view;
        if(bt.getId() == R.id.cancel){
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
        }
        if(bt.getId() == R.id.btn_signup){
            if(validation()){
              
            }else{
                CoordinatorLayout Clayout = (CoordinatorLayout)findViewById(R.id.snackbarlocation);
                snackbar = Snackbar.make(Clayout, "Please check the inputs!!!!", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }

        }
    }

    public boolean validation() {
        Boolean check = true;
         match = pattern.matcher(email_id.getText());
        if (TextUtils.isEmpty(username.getText())) {
            username.setError("UserName should not be Empty");
            username.setBackgroundResource(R.drawable.error_border);
            check =  false;
        }
        else if(TextUtils.isDigitsOnly(username.getText())){
            username.setError("Only Digits not allowed");
            username.setBackgroundResource(R.drawable.error_border);
            check =  false;
        }else {
            username.setError(null);
            username.clearFocus();
            username.setBackgroundResource(R.drawable.success_border);
        }

        if (TextUtils.isEmpty(email_id.getText())) {
            email_id.setError("Email-ID should not be Empty");
            email_id.setBackgroundResource(R.drawable.error_border);
            check =  false;
        }
        else if(!match.matches()){
            email_id.setError("Please enter valid Email-ID");
            email_id.setBackgroundResource(R.drawable.error_border);
            check =  false;
        }
        else {
            email_id.setError(null);
            email_id.clearFocus();
            email_id.setBackgroundResource(R.drawable.success_border);
        }
        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password should not be Empty");
            password.setBackgroundResource(R.drawable.error_border);
            check =  false;
        }else {
            password.setError(null);
            password.clearFocus();
            password.setBackgroundResource(R.drawable.success_border);
        }

        if (TextUtils.isEmpty(serial_id.getText())) {
            serial_id.setError("Serial ID should not be Empty");
            serial_id.setBackgroundResource(R.drawable.error_border);
            check =  false;
        }else {
            serial_id.setError(null);
            serial_id.clearFocus();
            serial_id.setBackgroundResource(R.drawable.success_border);
        }
        return check;
    }

}
