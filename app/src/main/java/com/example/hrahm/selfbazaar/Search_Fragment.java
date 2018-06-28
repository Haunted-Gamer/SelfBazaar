package com.example.hrahm.selfbazaar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Fragment extends Fragment {
    public static String crack="Wakanda";
    private List<items> allitems;
    private ListIterator<items> itr;
    private RecyclerView recyclerView;
    private itemsAdapter itemsadapter;
    private ArrayList<items> iList;
    private apiInterface API_INTERFACE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.search_fragment,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iList = new ArrayList<>();

        parseJSON(crack);


        return view;
    }

    public void parseJSON(String search){


        API_INTERFACE = apiClient.getApiClient().create(apiInterface.class);

        Call<List<items>> call = API_INTERFACE.searchItem(search);
        call.enqueue(new Callback<List<items>>() {
            @Override
            public void onResponse(Call<List<items>> call, Response<List<items>> response) {
                String check="";
                allitems = response.body();
                itr= allitems.listIterator();
                while(itr.hasNext())
                {
                    items ii = itr.next();

                    iList.add(new items(ii.getiId(),ii.getiName(),ii.getiDesc(),ii.getiPrice(),ii.getiPhoto(),ii.getiLocation(),ii.getiCategory(),ii.getuId()));

                    check = ii.getiPrice();

                }

                itemsadapter=new itemsAdapter(getContext(),iList);
                recyclerView.setAdapter(itemsadapter);
            }

            @Override
            public void onFailure(Call<List<items>> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
