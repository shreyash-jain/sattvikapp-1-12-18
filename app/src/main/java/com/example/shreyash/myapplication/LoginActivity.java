package com.example.shreyash.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    SharedPreferences sharedpreferences;
    public static final String myPreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = findViewById(R.id.text_email);
        _passwordText = findViewById(R.id.text_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.text_signup);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyAlertDialogStyle);

        //TODO: USe an appropriate style
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Authenticating...");
        progressDialog.setMessage("loading");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String email_refined = _emailText.getText().toString().replaceAll("\\W+","");

        FirebaseDatabase LoginReference = FirebaseDatabase.getInstance();
        DatabaseReference mLoginReference = LoginReference.getReference("student_sheet");
        mLoginReference.child("students").child(email_refined).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Person_Details personDetails2 = dataSnapshot.getValue(Person_Details.class);
                        try {
                            //checking if already registered or not
                            if(personDetails2.email.equals(email) && personDetails2.password.equals(password) )
                            {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                sharedpreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name, personDetails2.name);
                                editor.putString(Email, email);
                                editor.putString(Password,password);
                                editor.apply();
                                progressDialog.dismiss();
                                onLoginSuccess();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                onLoginFailed();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(LoginActivity.this, "You are not registered with this email id", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            onLoginFailed();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        onLoginFailed();
                        Log.w("registered or not", "loadPost:onCancelled", databaseError.toException());
                    }
                });

        /*new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    // On complete call either onLoginSuccess or onLoginFailed
                    //TODO: Check data from database and proceed accordingly
                    onLoginSuccess();
                    //If no data call function below
                    // onLoginFailed();
                    //progressDialog.dismiss();
                }
            }, 3000);*/
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        //TODO: Check if complete registration or not. Proceed accordingly
        //If complete
        Intent i = new Intent(LoginActivity.this, Dashboard.class);
        startActivity(i);
        finish();
        //Else
        //Intent i = new Intent(LoginActivity.this, Offline.class);
        //startActivity(i);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}