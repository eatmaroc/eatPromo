package com.eat.maroc.promo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homePage extends AppCompatActivity {
    Spinner spinnerVille,spinnerQuartier,spinnerType,spinnerPrix;
    RecyclerView listView;
    DatabaseReference databaseReference,mdata;
    ArrayList<String> cityList,quartierList,prixList;
    ArrayList<String> typeList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinnerPrix=findViewById(R.id.spinnerPrix);
        spinnerQuartier=findViewById(R.id.spinnerQuartier);
        spinnerType=findViewById(R.id.spinnerType);
        spinnerVille=findViewById(R.id.spinnerVille);
        listView=findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("villes");
        mdata = FirebaseDatabase.getInstance().getReference().child("list_type");
        cityList=new ArrayList<>();
        quartierList=new ArrayList<>();
        typeList = new ArrayList<>();
        prixList=new ArrayList<>();
        fetchVilles();
        fetchType();
        fetchPrix();
        if (spinnerVille != null) {
            spinnerVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    String villeSelected= cityList.get(position);
//                    String quartierSelected= quartierList.get(position);
//                    String typeSelected= typeList.get(position);
//                    String prixSelected= prixList.get(position);

                    String selectedVille = cityList.get(position);
                    fetchQuartiers(selectedVille);
//                    upDatePromo(villeSelected,"",typeSelected,prixSelected);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Logique en cas de sélection vide
                }
            });
        }
        else {
            Toast.makeText(homePage.this, "Erreur lors de la récupération du Spinner Ville", Toast.LENGTH_SHORT).show();
        }
        if (spinnerType != null) {
            spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedType =typeList.get(position);
                    fetchType();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Logique en cas de sélection vide
                }
            });
        }
        else {
            Toast.makeText(homePage.this, "Erreur lors de la récupération du Spinner Ville", Toast.LENGTH_SHORT).show();
        }
//        spinnerPrix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinnerQuartier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinnerVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });







    }

    private void upDatePromo(String villeSelected, String quartierSelected, String typeSelected, String prixSelected) {

    }

    private void fetchPrix() {
        prixList.clear();
        prixList.add("Prix");
        prixList.add("moin de 50");
        prixList.add("moin de 70");
        prixList.add("moin de 90");
        prixList.add("moin de 100");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, prixList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrix.setAdapter(adapter);
    }

    private void fetchType() {
        typeList.clear();
        typeList.add("Touts");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String typeName = snap.getKey();
                    typeList.add(typeName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homePage.this, "Failed to fetch villes: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        updateSpinnerAdapter();
    }

    private void updateTypeSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }
    private void fetchVilles() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cityList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String villeName = snap.getKey();
                    cityList.add(villeName);
                }
                updateTypeSpinnerAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homePage.this, "Failed to fetch type: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchQuartiers(String selectedVille) {
        DatabaseReference villeRef = databaseReference.child(selectedVille).child("quartiers");
        villeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quartierList.clear();
                for (DataSnapshot quartierSnapshot : snapshot.getChildren()) {
                    String quartierName = quartierSnapshot.getKey();
                    quartierList.add(quartierName);
                }
                updateQuartierSpinnerAdapter(); // Rafraîchir l'adaptateur du Spinner Quartier
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homePage.this, "Failed to fetch quartiers: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVille.setAdapter(adapter);

        // Appel pour mettre à jour le spinner du quartier après avoir mis à jour le spinner de la ville
        updateQuartierSpinnerAdapter();
    }

    private void updateQuartierSpinnerAdapter() {
        ArrayAdapter<String> quartierAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quartierList);
        quartierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuartier.setAdapter(quartierAdapter);
    }

}