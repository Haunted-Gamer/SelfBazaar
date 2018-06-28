package com.example.hrahm.selfbazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    public static final String PREFS_NAME = "userInfo";
    EditText emailText;
    private List <users> user;
    EditText passwordText;
    private ListIterator<users> itr;
    private apiInterface API_INTERFACE;
    SharedPreferences userSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        API_INTERFACE = apiClient.getApiClient().create(apiInterface.class);
        TextView Guest  = (TextView)findViewById(R.id.textView2);
        Button Signup = (Button)findViewById(R.id.button4);
        Button loginButton = (Button)findViewById(R.id.button2);
        emailText = (EditText)findViewById(R.id.editText);
        passwordText = (EditText) findViewById(R.id.editText3);

        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(i);            }
        });


        Signup.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v){
                                          Intent i = new Intent (getApplicationContext(), sign_up.class);
                                          startActivity(i);
                                      }
                                  }
        );

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail = emailText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                Call<List <users>> call = API_INTERFACE.login(userEmail,userPassword);
                call.enqueue(new Callback<List<users>>() {
                    @Override
                    public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                        users iterateHelper = new users();
                        StringBuilder sb1= new StringBuilder();
                        StringBuilder sb2= new StringBuilder();
                        user = response.body();
                        itr= user.listIterator();
                        if(!user.isEmpty()) {
                            while (itr.hasNext()) {

                                iterateHelper = itr.next();
                                sb1.append(iterateHelper.getEmail());
                                sb2.append(iterateHelper.getPass());


                            }
                            //Toast.makeText(getApplicationContext(),sb1.toString(),Toast.LENGTH_LONG).show();


                            if (userEmail.equals(sb1.toString()) && userPassword.equals(sb2.toString())) {

                                userSharedPreference = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = userSharedPreference.edit();
                                editor.putString("Email", iterateHelper.getEmail());
                                editor.putString("Name", iterateHelper.getName());
                                editor.putString("Dob", iterateHelper.getDob());
                                editor.putString("Phone", iterateHelper.getPhone());
                                editor.putString("Ipath", iterateHelper.getIpath());
                                editor.putString("Password", iterateHelper.getPass());
                                editor.putString("Id", iterateHelper.getId());
                                editor.commit();

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                            } else
                                Toast.makeText(getApplicationContext(), "Invalid USER/PASS", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(getApplicationContext(), "Invalid USER/PASS", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<List<users>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            }
        });



    }
}