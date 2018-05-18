package com.example.hrahm.selfbazaar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.StringPrepParseException;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sign_up extends AppCompatActivity {
    private static final int IMG_REQUEST = 786;
    Bitmap bitmap;
    TextView tsv ;
    String myFormat = "MM/dd/yy";
    EditText birthDay;
    EditText name;
    EditText email;
    EditText phone;
    private apiInterface API_INTERFACE;
    private apiInterface API_INTERFACES;
    int userCounter;
EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        API_INTERFACE = apiClient.getApiClient().create(apiInterface.class);
        API_INTERFACES = apiClient.getApiClient().create(apiInterface.class);
        final Calendar myCalendar = Calendar.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up);

        Button uploadImage = findViewById(R.id.button3);

        Button signUpButton = findViewById(R.id.button);
        birthDay = findViewById(R.id.editText6);
        tsv = findViewById(R.id.textView4);

        name= findViewById(R.id.editText2);
        email= findViewById(R.id.editText4);
        pass= findViewById(R.id.editText5);
        phone= findViewById(R.id.editText7);


        counterMethod();
        uploadImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/* ");
                startActivityForResult(pickIntent, IMG_REQUEST);

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                String NAME = name.getText().toString();
                String EMAIL = email.getText().toString();
                String PASSWORD = pass.getText().toString();
                String PHONE = phone.getText().toString();
                final String DOB = birthDay.getText().toString();
                final String IMAGE=imageToString();
                String TITLE = String.valueOf(userCounter+1);

                Call<JsonObject> calle = API_INTERFACE.registration(NAME,EMAIL,PASSWORD,DOB,PHONE,TITLE,IMAGE);
                calle.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        Toast.makeText(getApplicationContext(),"Account Created Successfully",Toast.LENGTH_LONG).show();
                        Intent i = new Intent (getApplicationContext(), login.class);
                        startActivity(i);

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });



            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                birthDay.setText(sdf.format(myCalendar.getTime()));
            }

        };

        birthDay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                new DatePickerDialog(sign_up.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void counterMethod() {

        Call<JsonArray> call = API_INTERFACE.getNumber();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray object = response.body();
                JsonElement lol = object.get(0);

                String jsonString = lol.toString();
                StringBuilder sb = new StringBuilder();

                int i;
                for (i = 8; i < jsonString.length(); i++) {

                    if (jsonString.charAt(i) > 47 && jsonString.charAt(i) < 58) {

                        sb.append(jsonString.charAt(i));
                    }
                }
                userCounter =Integer.parseInt(sb.toString());
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }
    public String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte =byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();

            tsv.setText(path.toString());

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }





}



