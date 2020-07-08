package com.wala.apolloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    boolean lit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Button light = findViewById(R.id.light);
        Button right = findViewById(R.id.right);
        Button left = findViewById(R.id.left);
        Button stop = findViewById(R.id.stop);

        light.setOnClickListener(v->{
            lit = !lit;
            myRef.child("lights").setValue(lit ? "on": "off");
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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("lights").getValue(String.class);
                if (value != null) {
                    lit = value.equals("on");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
