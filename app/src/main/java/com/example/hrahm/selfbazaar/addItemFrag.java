package com.example.hrahm.selfbazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addItemFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    private apiInterface API_INTERFACE;
    SharedPreferences userSharedPreferences;
    String SpinnerText;
    EditText ItemName;
    EditText ItemDesc;
    EditText ItemPrice;
    EditText ItemLoc;
    Bitmap bitmap;
    Button addImgBtn;
    Button addItemBtn;
    ImageView imageView;
    Spinner spinner;
    private boolean photosubmitted=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.item_add,container,false);


        userSharedPreferences = getActivity().getSharedPreferences(login.PREFS_NAME,0);
        API_INTERFACE = apiClient.getApiClient().create(apiInterface.class);

        ItemName = (EditText) view.findViewById(R.id.editText14);
        ItemDesc = (EditText) view.findViewById(R.id.editText15);
        ItemPrice = (EditText) view.findViewById(R.id.editText16);
        ItemLoc = (EditText) view.findViewById(R.id.editText13);


        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Categories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        imageView = view.findViewById(R.id.imageView2);
        addImgBtn = view.findViewById(R.id.button9);
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photosubmitted=true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        addItemBtn = view.findViewById(R.id.button10);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName= ItemName.getText().toString();
                String itemDesc= ItemDesc.getText().toString();
                String itemPrice= ItemPrice.getText().toString();
                String itemLoc= ItemLoc.getText().toString();
                String itemCategory= SpinnerText;
                String Picture = imageToString();
                String uid = userSharedPreferences.getString("Id", "GuestID");

                if (!photosubmitted){
                    Toast.makeText(getContext(),"Submit Item Photo", Toast.LENGTH_SHORT).show();
                }

                if (uid.equals("GuestID")){
                    Toast.makeText(getContext(),"Guests cannot add items", Toast.LENGTH_SHORT).show();
                }

                if (photosubmitted && !uid.equals("GuestID"))
                {
                    Call<JsonObject> call = API_INTERFACE.itemAdd(itemName,itemDesc,itemPrice,Picture,itemLoc,itemCategory,uid);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            Toast.makeText(getContext(),"Item added",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();


                        }
                    });

                }

            }
        });




    return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
    }


    public String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte =byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerText = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
