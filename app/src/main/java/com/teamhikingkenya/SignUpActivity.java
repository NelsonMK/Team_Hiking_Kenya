package com.teamhikingkenya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.teamhikingkenya.utils.RequestHandler;
import com.teamhikingkenya.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText editFirst, editLast, editGender, editPhone, editEmail, editLocation, editUserType, editPassword, editConfirmPassword;

    String gender_type, user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

    }

    void init(){

        editFirst = findViewById(R.id.editTextFirstName);
        editLast = findViewById(R.id.editTextLastName);
        editGender = findViewById(R.id.editTextGender);
        editPhone = findViewById(R.id.editTextPhone);
        editEmail = findViewById(R.id.editTextEmail);
        editLocation = findViewById(R.id.editTextLocation);
        editUserType = findViewById(R.id.editTextUserType);
        editPassword = findViewById(R.id.editTextPassword);
        editConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender();
            }
        });

        editUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type();
            }
        });

        findViewById(R.id.linearSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.logIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });
    }

    void gender(){
        final String[] gender_choice = getResources().getStringArray(R.array.gender);
        final int itemSelected = 0;
        new AlertDialog.Builder(this)
                .setTitle("Select you gender")
                .setSingleChoiceItems(gender_choice, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        gender_type = gender_choice[which];
                        editGender.setText(gender_type);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    void user_type(){
        final String[] user_choice = getResources().getStringArray(R.array.user_type);
        final int itemSelected = 0;
        new AlertDialog.Builder(this)
                .setTitle("Select you user type")
                .setSingleChoiceItems(user_choice, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        user_type = user_choice[which];
                        editUserType.setText(user_type);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void registerUser() {
        final String first_name = editFirst.getText().toString().trim();
        final String last_name = editLast.getText().toString().trim();
        final String gender = editGender.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String location = editLocation.getText().toString().trim();
        final String userType = editUserType.getText().toString();
        String class1 = null;
        final String password = editPassword.getText().toString().trim();
        final String confirm_password = editConfirmPassword.getText().toString().trim();

        switch (userType){
            case "Hiker": class1="1";
                break;
            case "Manager": class1="2";
                break;
        }

        //validations
        String mobile_pattern = "^07[0-9]{8}$";
        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";

        if (TextUtils.isEmpty(first_name)) {
            editFirst.setError("Please enter your first name!");
            editFirst.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(last_name)) {
            editLast.setError("Please enter your last name!");
            editLast.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editPhone.setError("Please enter your phone number!");
            editPhone.requestFocus();
            return;
        }
        if (editPhone.length()<10){
            editPhone.setError("Phone number cannot be less than 10 digits");
            editPhone.requestFocus();
            return;
        }
        if (!editPhone.getText().toString().matches(mobile_pattern)){
            editPhone.setError("Enter a valid phone number");
            editPhone.requestFocus();
            return;
        }
        if (!android.util.Patterns.PHONE.matcher(phone).matches()){
            editPhone.setError("Enter a valid phone number");
            editPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Please enter your email address!");
            editEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter a valid email address!");
            editEmail.requestFocus();
            return;
        }
        if (!editEmail.getText().toString().matches(email_pattern)){
            editEmail.setError("Enter a valid email address!");
            editEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(location)){
            editLocation.setError("Enter your location");
            editLocation.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter your password!");
            editPassword.requestFocus();
            return;
        }
        if (!password.equals(confirm_password)){
            editPassword.setError("Passwords do not match!");
            editPassword.requestFocus();
            editConfirmPassword.setError("Passwords do not match!");
            editConfirmPassword.requestFocus();
            return;
        }
        //Toast.makeText(this, "gender " + location + "user type" + class1, Toast.LENGTH_SHORT).show();
        //if it passes all the validations
        //executing the async task
        RegisterUser registerUser = new RegisterUser(first_name,last_name,gender,phone,email,class1,location,password);
        registerUser.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class RegisterUser extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        private String first_name,last_name,gender,phone,email,class1,location,password;
        RegisterUser(String first_name,String last_name, String gender, String phone, String email, String class1, String location, String password){
            this.first_name = first_name;
            this.last_name = last_name;
            this.gender = gender;
            this.phone = phone;
            this.password = password;
            this.class1 = class1;
            this.location = location;
            this.email = email;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar = findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("gender", gender);
            params.put("phone", phone);
            params.put("email", email);
            params.put("class", class1);
            params.put("location", location);
            params.put("password", password);

            //returning the response
            return requestHandler.sendPostRequest(URLS.URL_REGISTER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SignUp","signUp : "+s);
            //hiding the progressbar after completion
            //progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {
                    //take the user to log in screen
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                    finish();

                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}