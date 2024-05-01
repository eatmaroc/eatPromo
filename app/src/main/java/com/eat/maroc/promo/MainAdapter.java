package com.eat.maroc.promo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;

import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Item> listPromo;
    private Activity activity;
    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; //milliseconds

    public MainAdapter(Context context, ArrayList<Item> listPromo, Activity activity) {
        this.context = context;
        this.listPromo = listPromo;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item promo = listPromo.get(position);
        holder.title.setText(promo.getTitle());
        holder.type.setText(promo.getType());
        holder.prix.setText(promo.getPrix());
        Picasso.get().load(promo.getImage()).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                long clickTime = System.currentTimeMillis();
//                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
//                    openDetaillesActivity(promo);
//                }
//                lastClickTime = clickTime;
                openDetaillesActivity(promo);
            }
        });
    }

    private void openDetaillesActivity(Item item) {
        Intent intent = new Intent(context, Detailles.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("type", item.getType());
        intent.putExtra("prix", item.getPrix());
        intent.putExtra("ville", item.getVille());
        intent.putExtra("quartier", item.getQuartier());

        intent.putExtra("image", item.getImage());
        intent.putExtra("whatsapp", item.getWhatsapp());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("adress", item.getAdress());
        intent.putExtra("location", item.getLocation());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listPromo.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, type, prix;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCarte);
            title = itemView.findViewById(R.id.titleCarte);
            type = itemView.findViewById(R.id.typeC);
            prix = itemView.findViewById(R.id.prixC);
        }
    }
}
