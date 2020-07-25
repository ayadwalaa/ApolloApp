package com.wala.apolloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    boolean lit;
    boolean red_bool, green_bool, blue_bool = false;
    String light_intensity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Button light = findViewById(R.id.light);
        Button light2 = findViewById(R.id.light2);

        Button right = findViewById(R.id.right);
        Button left = findViewById(R.id.left);
        Button stop = findViewById(R.id.stop);
        Button red = findViewById(R.id.red);
        Button green = findViewById(R.id.green);
        Button blue = findViewById(R.id.blue);


        light.setOnClickListener(v->{
            myRef.child("intensity").setValue("increase");
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                myRef.child("intensity").setValue("neutral");
            }, 100);
        });
        light2.setOnClickListener(v->{
            myRef.child("intensity").setValue("decrease");
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                myRef.child("intensity").setValue("neutral");
            }, 100);
        });

        right.setOnClickListener(b->{
            myRef.child("direction").setValue("forward");
        });
        left.setOnClickListener(n->{
            myRef.child("direction").setValue("backward");
        });
        stop.setOnClickListener(m->{
            myRef.child("direction").setValue("stop");
        });
        red.setOnClickListener(c->{
            myRef.child("status").setValue("searching");

        });
        blue.setOnClickListener(c->{
            myRef.child("status").setValue("found");

        });
        green.setOnClickListener(c->{
            myRef.child("status").setValue("not_found");

        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("lights").getValue(String.class);
                if (value != null) {
                    lit = value.equals("on");
                }
                light_intensity = dataSnapshot.child("light_intensity").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
