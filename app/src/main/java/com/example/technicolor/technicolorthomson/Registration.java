package com.example.technicolor.technicolorthomson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    static EditText username;
    static EditText email_id;
    static EditText password;
    static EditText serial_id;
    static String regex = "^(.+)@(.+)$";
    static Pattern pattern ;
    static Matcher match;
    static Context mainscreen;
    static Snackbar snackbar;
    public static final int PASSWORD_LENGTH = 8;

    public Registration() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = findViewById(R.id.signup_input_name);
        email_id = findViewById(R.id.signup_input_email);
        password = findViewById(R.id.signup_input_password);
        serial_id = findViewById(R.id.serial_id_text);
        pattern = Pattern.compile(regex);
        mainscreen = Registration.this;
    }

    public void onClick(View view) {
        Button bt = (Button) view;
        if(bt.getId() == R.id.cancel){
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
        }
        if(bt.getId() == R.id.btn_signup){
            if(validation()){
                new serverAPI("Registration ", mainscreen).execute();
            }else{
                createsnackbar(1);
            }

        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }

    public boolean validation() {
        Boolean check = true;
         match = pattern.matcher(email_id.getText());
        if (TextUtils.isEmpty(username.getText())) {
            username.setError("UserName should not be Empty");
            username.setBackgroundResource(R.drawable.error_border);
            check =  false;
        } else if (TextUtils.isDigitsOnly(username.getText())) {
            username.setError("Only Digits not allowed");
            username.setBackgroundResource(R.drawable.error_border);
            check = false;
        } else if (TextUtils.getTrimmedLength(username.getText()) < 5) {
            username.setError("Please enter your full Name");
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
        } else if (!is_Valid_Password(password.getText().toString())) {
            password.setError("1. A password must have at least eight characters.\n" +
                    "2. A password consists of only letters and digits.\n" +
                    "3. A password must contain at least two digits.");
            password.setBackgroundResource(R.drawable.error_border);
            check = false;
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


    public void createsnackbar(int error_code) {
        String error_string = "";
        int backgroundcolor = Color.RED;
        switch (error_code) {
            case 0:
                error_string = "User Profile created successfully";
                backgroundcolor = Color.GREEN;
                break;
            case 1:
                error_string = "Please check the inputs!!!!";
                break;
            case 2:
                error_string = "Email Id already existing";
                break;
            case 3:
                error_string = "Operation failed";
                break;
        }
        CoordinatorLayout Clayout = findViewById(R.id.snackbarlocation);
        snackbar = Snackbar.make(Clayout, error_string, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(backgroundcolor);
        snackbar.show();
    }

    public boolean is_Valid_Password(String password) {

        if (password.length() < PASSWORD_LENGTH) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }


        return (charCount >= 2 && numCount >= 2);
    }

    public boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    public boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

}
