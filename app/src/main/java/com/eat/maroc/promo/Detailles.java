package com.eat.maroc.promo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class Detailles extends AppCompatActivity {
    ImageView image;
    Button order;
    TextView titre,type,prix,detailles;


    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        image=findViewById(R.id.imgDeatilles);
        titre=findViewById(R.id.titleDetailles);
        type=findViewById(R.id.typeDD);
        prix=findViewById(R.id.prixD);
        detailles=findViewById(R.id.discriptionDetailles);
        order=findViewById(R.id.btnOrder);

        titre.setText(getIntent().getStringExtra("title"));
        type.setText(getIntent().getStringExtra("type"));
        prix.setText(getIntent().getStringExtra("prix"));

        // Load the image using Picasso
        int imageUrl = getIntent().getIntExtra("image",0);
        if (imageUrl != 0 ) {
            Picasso.get().load(imageUrl).into(image);
        }
        // TODO: 4/24/2024 immage and data base
    }}
