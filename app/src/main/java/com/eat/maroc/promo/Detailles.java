package com.eat.maroc.promo;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Detailles extends AppCompatActivity {
    ImageView image;
    TextView titre,type,prix,ville,cartier,detailles;
    WebView map;
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
        titre=findViewById(R.id.titreDeatailles);
        type=findViewById(R.id.typeDetailles);
        prix=findViewById(R.id.prixDetailles);
        ville=findViewById(R.id.villedetailles);
        cartier=findViewById(R.id.cartierDetailles);
        detailles=findViewById(R.id.discriptionDetailles);
        map=findViewById(R.id.imgDeatilles);
    }
}