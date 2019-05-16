package com.example.technicolor.technicolorthomson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
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
            Intent myIntent = new Intent(this, Registation.class);
            startActivity(myIntent);
        }
        if(bt.getId() == R.id.sign_in){
            if(validation()){
            }else{
                Log.e("I am not okay", "I am not okay");
            }

        }

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
}
