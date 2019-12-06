package com.wozzytheprogrammer.kwproperty.Customer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wozzytheprogrammer.kwproperty.R;

public class FindAgentsActivity extends AppCompatActivity {
    private ProgressDialog loadingBar;
    Button findAgentButton;

    private LatLng customersLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_agents);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupPage();


    }

    private void setupPage() {
        loadingBar = new ProgressDialog(this);
        findAgentButton = findViewById(R.id.find_agent_confirm);

        findAgentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findAnAgent();
            }
        });

    }

    private int radius = 1;
    private Boolean agentFound = false;


    GeoQuery geoQuery;

    private void findAnAgent() {
        loadingBar.setTitle("Locating Real Estate Agent...");
        loadingBar.setMessage("Please wait, we are scanning your location...");
        loadingBar.show();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
        GeoFire geoFire = new GeoFire(ref);

        DatabaseReference agentLocation = FirebaseDatabase.getInstance().getReference().child("agentsAvailable");
        agentLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String agentsOnlineIds = child.getKey();
                        Log.e("key", agentsOnlineIds);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
