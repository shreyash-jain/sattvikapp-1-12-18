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

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.shreyash.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.shreyash.utils.Constants.email;


public class LoginActivity extends AppCompatActivity {
    private DatabaseReference miDatabase;
    private static final String TAG = "LoginActivity";

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    TextView password_forget;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.text_email);
        passwordText = findViewById(R.id.text_password);
      password_forget =findViewById(R.id.forget_pass);

        loginButton = findViewById(R.id.btn_login);
        signupLink = findViewById(R.id.text_signup);

        password_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RequestPassword.class);

                startActivity(i);



            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

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

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyAlertDialogStyle);

        //TODO: USe an appropriate style
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Authenticating...");
        progressDialog.setMessage("loading");
        progressDialog.show();

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        final String emailRefined = emailText.getText().toString().replaceAll("\\W+","");

        FirebaseDatabase LoginReference = FirebaseDatabase.getInstance();
        DatabaseReference mLoginReference = LoginReference.getReference("student_sheet");
        mLoginReference.child("students").child(emailRefined).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PersonDetails personDetails2 = dataSnapshot.getValue(PersonDetails.class);
                        try {
                            //checking if already registered or not
                            if(personDetails2.email.equals(email) && personDetails2.password.equals(password) )
                            {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.name, personDetails2.name);
                                editor.putString(Constants.email, email);
                                editor.putString(Constants.password,password);
                                editor.putString(Constants.isactive,personDetails2.isactive);
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
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        final String email = sharedPreferences.getString(Constants.email,"");
        final String email_refined = email.replaceAll("\\W+", "");

        FirebaseDatabase PostReference = FirebaseDatabase.getInstance();
        DatabaseReference cPostReference = PostReference.getReference("cancel_sheet");
        cPostReference.child(email_refined).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<CancelDetails> cancelDetailsArray = new ArrayList<>();
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            cancelDetailsArray.add(child.getValue(CancelDetails.class));
                        }
                        //Saving to internal storage
                        String filename = "CancelData";
                        FileOutputStream outStream;
                        try {
                            outStream = openFileOutput(filename, Context.MODE_PRIVATE);
                            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
                            // Save size first
                            objectOutStream.writeInt(cancelDetailsArray.size());
                            for(CancelDetails var:cancelDetailsArray)
                                objectOutStream.writeObject(var);
                            objectOutStream.close();
                            outStream.close();
                        } catch (Exception e) {
                            //Toast.makeText(LoginActivity.this, "oho", Toast.LENGTH_SHORT).show();
                            Log.e("writer error", e+"");
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("activated or not", "loadPost:onCancelled", databaseError.toException());
                    }
                });
        loginButton.setEnabled(true);

        String active = sharedPreferences.getString(Constants.isactive,"0");
        if(active.equals("0"))
        {
            Intent i = new Intent(LoginActivity.this, Offline.class);
            i.putExtra("EXTRA", "notopenFragment");
            startActivity(i);
            finish();
        }
        else{
            Intent i = new Intent(LoginActivity.this, Dashboard.class);
            i.putExtra("EXTRA", "notopenFragment");
            startActivity(i);
            finish();
        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}