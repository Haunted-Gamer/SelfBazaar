package com.example.hrahm.selfbazaar;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.viewHolder> {

    private Context mContext;
    private ArrayList<items> itemsList;

    public itemsAdapter(Context context, ArrayList<items> iList){
        mContext=context;
        itemsList=iList;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.items,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        items currentItem = itemsList.get(position);

        String i_name = currentItem.getiName();
        String i_price = currentItem.getiPrice();
        String i_photoLoc = currentItem.getiPhoto();
        String i_picture = apiClient.BASE_URL.concat(i_photoLoc);

        holder.itemName.setText(i_name);
        holder.itemPrice.setText(i_price);
        Picasso.get()
                .load(i_picture)
                .fit()
                .centerInside()
                .into(holder.itemPic);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        public ImageView itemPic;
        public TextView itemPrice;
        public TextView itemName;
        public viewHolder(View itemView) {
            super(itemView);
            itemPic = itemView.findViewById(R.id.image_view);
            itemPrice = itemView.findViewById(R.id.itemprice);
            itemName = itemView.findViewById(R.id.itemname);
        }
    }
}
