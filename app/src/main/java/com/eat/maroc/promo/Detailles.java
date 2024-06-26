package com.eat.maroc.promo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;


public class Detailles extends AppCompatActivity {
    ImageView image;
    Button order;
    TextView titre,type,prix,detailles;

    FrameLayout frameLayout;

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
        frameLayout=findViewById(R.id.linearLayout3);

        titre.setText(getIntent().getStringExtra("title"));
        type.setText(getIntent().getStringExtra("type"));
        prix.setText(getIntent().getStringExtra("prix")+" DH");

        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
        bottomSheetBehavior.setPeekHeight(350);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        // Load the image using Picasso
       // image.setImageResource(getIntent().getIntExtra("image",0));



        // TODO: 4/24/2024 immage and data base
        String descrip = getIntent().getStringExtra("description");
        detailles.setText(descrip);


        String imageUrl = getIntent().getStringExtra("image");
        Picasso.get().load(imageUrl).into(image);

        order.setOnClickListener(v->{
            Intent intent = new Intent(this, order.class);
            intent.putExtra("adress", getIntent().getStringExtra("adress"));
            intent.putExtra("location",getIntent().getStringExtra("location") );
            intent.putExtra("whatsapp",getIntent().getStringExtra("whatsapp") );
            startActivity(intent);
        });

    }}
