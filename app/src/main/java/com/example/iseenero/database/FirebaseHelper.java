package com.example.iseenero.database;

import androidx.annotation.NonNull;

import com.example.iseenero.ui.search.Sungai;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Sungai> sungaiArrayList = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db)
    {
        this.db = db;
    }

    //Write IF NOT NULL
    public Boolean save(Sungai sungai)
    {
        if(sungai == null)
        {
            saved = false;
        }
        else
        {
            try
            {
                db.child("Sungai").push().setValue(sungai);
                saved = true;
            } catch (DatabaseException e)
            {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        sungaiArrayList.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Sungai sungai = ds.getValue(Sungai.class);
            sungaiArrayList.add(sungai);
        }
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Sungai> retrieve()
    {

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {
                fetchData(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {
                fetchData(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return sungaiArrayList;
    }
}
