package com.eat.maroc.promo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class order extends AppCompatActivity {
    WebView webView;
    TextView text;
    Button call;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        call=findViewById(R.id.btnCall);
        text = findViewById(R.id.text);
        webView = findViewById(R.id.webLocation);
        text.setText(getIntent().getStringExtra("adress"));
        String frameCode = getIntent().getStringExtra("location");
        String phoneNumber=getIntent().getStringExtra("whatsapp");
        call.setText(phoneNumber);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(frameCode, "text/html", "utf-8");
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0682749086"));
                startActivity(intent);
//                } catch (Exception e) {
//                    Toast.makeText(order.this, e.toString(), Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}