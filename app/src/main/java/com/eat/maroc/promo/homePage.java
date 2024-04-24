package com.eat.maroc.promo;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homePage extends AppCompatActivity   {
    Spinner spinnerVille,spinnerQuartier,spinnerType,spinnerPrix;
    RecyclerView listView;
    DatabaseReference databaseReference,mdata;
    ArrayList<String> cityList,quartierList,prixList;
    ArrayList<String> typeList;
    MainAdapter myAdapter;
    ArrayList<Item> listPromo;



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
        listView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("villes");
        mdata = FirebaseDatabase.getInstance().getReference().child("list_type");
        cityList=new ArrayList<>();
        quartierList=new ArrayList<>();
        typeList = new ArrayList<>();
        prixList=new ArrayList<>();

        fetchVilles();
        fetchType();
        fetchPrix();
        listView.setHasFixedSize(true);
        listPromo=new ArrayList<>();
        myAdapter=new MainAdapter(this,listPromo,this);
        listView.setAdapter(myAdapter);
//        myAdapter.setClickListener(this);

        if (spinnerVille != null) {
            spinnerVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedVille = cityList.get(position);
                    fetchQuartiers(selectedVille);
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
                    afficher();
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

        spinnerQuartier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                afficher();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                afficher();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPrix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                afficher();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
    private void afficher() {
        String selectedVille = spinnerVille.getSelectedItem() != null ? spinnerVille.getSelectedItem().toString() : "";
        String selectedQuartier = spinnerQuartier.getSelectedItem() != null ? spinnerQuartier.getSelectedItem().toString() : "";
        String selectedType = spinnerType.getSelectedItem() != null ? spinnerType.getSelectedItem().toString() : "Touts";
        String selectedPrix = spinnerPrix.getSelectedItem() != null ? spinnerPrix.getSelectedItem().toString() : "Prix";

        // Clear the existing list of promotions
        listPromo.clear();

        // Get a reference to the promotions for the selected city and neighborhood
        DatabaseReference promotionsRef = databaseReference.child(selectedVille).child("quartiers").child(selectedQuartier);

        // Fetch promotions data once
        promotionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot promoSnapshot : snapshot.getChildren()) {
                    // Get the promotion details
                    String title = promoSnapshot.child("title").getValue(String.class);
                    String type = promoSnapshot.child("type").getValue(String.class);
                    String prix = promoSnapshot.child("prix").getValue(String.class);
                    String image = promoSnapshot.child("image").getValue(String.class);
                    String ville = promoSnapshot.child("ville").getValue(String.class);
                    String quartier = promoSnapshot.child("quartier").getValue(String.class);
                    String whatsapp = promoSnapshot.child("whatsapp").getValue(String.class);
                    String adress = promoSnapshot.child("adress").getValue(String.class);
                    String description = promoSnapshot.child("description").getValue(String.class);

                    // Check if the promotion matches the selected type and price filters
                    if ((selectedType.equals("Touts") || (type != null && type.equals(selectedType))) &&
                            (selectedPrix.equals("Prix") || isPriceInRange(prix, selectedPrix))) {
                        // Create a PromoCarte object
                        Item promo = new Item(title, type, prix, image, whatsapp, description, adress, ville, quartier);

                        // Add the promotion to the list
                        listPromo.add(promo);
                    }
                }

                // Remove the last item from the list if it's not filtered by type or price
                if (!listPromo.isEmpty() && selectedType.equals("Touts") && selectedPrix.equals("Prix")) {
                    listPromo.remove(listPromo.size() - 1);
                }

                // Notify the adapter that the data set has changed
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(homePage.this, "Failed to fetch promotions: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    // Function to check if the price is in the selected range
    private boolean isPriceInRange(String price, String selectedPrice) {
        if (price != null && !price.isEmpty() && !selectedPrice.isEmpty()) {
            switch (selectedPrice) {
                case "moin de 50":
                    return Double.parseDouble(price) < 50;
                case "moin de 70":
                    return Double.parseDouble(price) < 70;
                case "moin de 90":
                    return Double.parseDouble(price) < 90;
                case "moin de 100":
                    return Double.parseDouble(price) < 100;
                case "plus de 100":
                    return Double.parseDouble(price) >= 100;
                default:
                    return false;
            }
        }
        return false;
    }


    private void fetchPrix() {
        prixList.clear();
        prixList.add("Prix");

        prixList.add("moin de 50");
        prixList.add("plus de100");
        prixList.add("moin de 70");
        prixList.add("moin de 90");
        prixList.add("moin de 100");
        prixList.add("plus de 100");
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
                updateTypeSpinnerAdapter();
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
                updateSpinnerAdapter();
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
    public void onItemClick(View view, int position) {
        // Get the item at the clicked position
        Item item = listPromo.get(position);

        // Open the new activity, passing item details to it
        Intent intent = new Intent(this, Detailles.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("type", item.getType());
        intent.putExtra("prix", item.getPrix());
        intent.putExtra("image", item.getImage());

        startActivity(intent);
    }



}
