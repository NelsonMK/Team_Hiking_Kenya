package com.teamhikingkenya;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.teamhikingkenya.database.DatabaseHandler;
import com.teamhikingkenya.database.PrefManager;
import com.teamhikingkenya.model.User;
import com.teamhikingkenya.utils.RequestHandler;
import com.teamhikingkenya.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        init();
    }

    void init(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //if user presses on login calling the method login
        findViewById(R.id.linearLogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });
        //if user presses on not registered
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });
        //if the user clicks on forgot password

    }

    private void userLogin() {
        //first getting the values
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email!");
            editTextEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        UserLogin userLogin = new UserLogin(email,password);
        userLogin.execute();
    }

    @SuppressLint("StaticFieldLeak")
    class UserLogin extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar;
        String email, password;
        UserLogin(String email,String password) {
            this.email = email;
            this.password = password;
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
            params.put("email", email);
            params.put("password", password);

            //returning the response
            return requestHandler.sendPostRequest(URLS.URL_LOGIN, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("user");
                    String class1 = userJson.getString("class");
                    String status = userJson.getString("status");

                    //creating a new user object
                    User user = new User(
                            userJson.getInt("id"),
                            userJson.getString("first_name"),
                            userJson.getString("last_name"),
                            userJson.getString("phone"),
                            userJson.getString("email"),
                            userJson.getString("gender"),
                            userJson.getString("date_of_birth"),
                            userJson.getString("location"),
                            userJson.getInt("class"),
                            userJson.getInt("status"),
                            userJson.getInt("archive")
                    );

                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(), "Account not confirmed!", Toast.LENGTH_SHORT).show();
                    } else {
                        //storing the log in session in shared preferences
                        PrefManager.getInstance(getApplicationContext()).setUserLogin(user);
                        //storing the user to SQLite database
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.addUser(user);
                        //starting the respective activities according to user types
                        Intent hiker = new Intent(LogInActivity.this, MainActivity.class);
                        hiker.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(hiker);
                        /*switch (class1) {
                            case "1":
                                Intent customer = new Intent(LogIn.this, MainActivity.class);
                                customer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(customer);
                                break;
                            case "2":
                                Intent coach = new Intent(LogIn.this, Employee.class);
                                coach.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(coach);
                                break;
                        }*/
                    }
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