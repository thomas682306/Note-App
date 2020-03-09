package com.example.multiplerecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplerecycler.login_signup.profile_page;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements noteAdapter.onNoteClick {

    ImageView addNoteButton;
    TextView heading, description, date, day;
    ImageView bellicon;

    CollectionReference cf;
    String uid;

    RecyclerView recyclerView;
    noteAdapter noteAdapter;
    FirestoreRecyclerOptions<modelClass_note> recyclerOptions;
    ImageButton profile_Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        addNoteButton = findViewById(R.id.addnoteButton);
        heading = findViewById(R.id.heading);
        description = findViewById(R.id.textView3);
        date = findViewById(R.id.textView4);
        profile_Image=findViewById(R.id.profile_pic);
        day = findViewById(R.id.day);
        bellicon = findViewById(R.id.bellIcon);
        uid = FirebaseAuth.getInstance().getUid();
        cf = FirebaseFirestore.getInstance().collection("users").document(uid).collection("notes");


        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, addNoteActivity.class);
                startActivity(intent);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
                addNoteButton.startAnimation(animation);

            }
        });
        Query query = cf.orderBy("priority");


        recyclerOptions = new FirestoreRecyclerOptions.Builder<modelClass_note>().setQuery(query, modelClass_note.class).build();
        noteAdapter = new noteAdapter(recyclerOptions,getApplicationContext(),this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalFadingEdgeEnabled(true);
        recyclerView.setFadingEdgeLength(50);
        noteAdapter.startListening();


        profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,profile_page.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onNoteitemSelected(int position) {
        String peHeading,peDescription,peDate,peDay;
        peDate=noteAdapter.getItem(position).getDate();
        peDay=noteAdapter.getItem(position).getDay();
        peDescription=noteAdapter.getItem(position).getDescription();
        peHeading=noteAdapter.getItem(position).getHeading();
        Intent  intent = new Intent(MainActivity.this,addNoteActivity.class);
        intent.putExtra("peHeading",peHeading);
        intent.putExtra("peDescription",peDescription);
        intent.putExtra("pedate",peDate);
        intent.putExtra("peday",peDay);
        intent.putExtra("code",1);
        startActivity(intent);





    }


    @Override
    public void delete() {


    }

    @Override
    public void alarmSet() {

    }


}
