package com.example.fitnessapplication.FitnessApp.UsersActivities.Specialists;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitnessapplication.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchSpecialistList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Specialist> specialistArrayList;
    SpecialistAdapter specialistAdapter;
    FirebaseFirestore firebaseFirestore;
    SpecialistAdapter.RecyclerViewClickListener recyclerViewClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_specialist_list);

        recyclerView = findViewById(R.id.specialistList);
        recyclerView.setHasFixedSize(true);

        firebaseFirestore = FirebaseFirestore.getInstance();
        specialistArrayList = new ArrayList<Specialist>();
        specialistAdapter = new SpecialistAdapter(specialistArrayList, recyclerViewClickListener);
    }

    private void readSpecialistsFromFirestore(){
        firebaseFirestore.collection("specialists")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                    }
                });
    }
}

