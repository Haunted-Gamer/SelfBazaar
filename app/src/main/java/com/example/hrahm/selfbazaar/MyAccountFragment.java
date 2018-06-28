package com.example.hrahm.selfbazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;

public class MyAccountFragment extends Fragment {
    Button buttonLogout;
    EditText Name;
    EditText Email;
    EditText Password;
    EditText Dob;
    EditText Phone;
TextView id ;
ImageView iv;
Button addItembtn;
    private apiInterface API_INTERFACE;
    SharedPreferences SP;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_my_profile,container,false);
        SP = getContext().getSharedPreferences(login.PREFS_NAME,0);
        Name =view.findViewById(R.id.editText9);
        Email=view.findViewById(R.id.editText10);
        Password=view.findViewById(R.id.editText11);
        Dob=view.findViewById(R.id.editText12);
        Phone=view.findViewById(R.id.editText8);
        id=view.findViewById(R.id.textView3);
        iv=view.findViewById(R.id.imageView);
        addItembtn=view.findViewById(R.id.button7);
        Name.setText(SP.getString("Name", "Guest Name"));
        Email.setText(SP.getString("Email", "Guest@Guest.com"));
        Password.setText(SP.getString("Password", "Password"));
        Dob.setText(SP.getString("Dob", "00/00/00"));
        Phone.setText(SP.getString("Phone","000000"));
        id.setText("ID# ".concat(SP.getString("Id", "999999")));

        if (SP.contains("Ipath"))
        {
            String imagePath = apiClient.BASE_URL.concat(SP.getString("Ipath", "GuestImage"));

            Picasso.get()
                    .load(imagePath)
                    .resize(200, 300)
                    .centerCrop()
                    .into(iv);
        }
        addItembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new Fragment();
                myFragment = new addItemFrag();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();


            }
        });



        buttonLogout = (Button) view.findViewById(R.id.button6);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SP.contains("Email"))
                {
                    SP.edit().clear().commit();
                }
                Intent i = new Intent (getActivity().getApplicationContext(), login.class);
                startActivity(i);
                Toast.makeText(getActivity().getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });
return view;

    }
}
