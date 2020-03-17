package com.example.multiplerecycler.mainActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.multiplerecycler.Notification.AlarmReciever;
import com.example.multiplerecycler.Notification.NotificationHelper;
import com.example.multiplerecycler.R;
import com.example.multiplerecycler.add_Note.addNoteActivity;
import com.example.multiplerecycler.login_signup.profile_page;
import com.example.multiplerecycler.login_signup.timePickerFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements com.example.multiplerecycler.mainActivity.noteAdapter.onNoteClick, TimePickerDialog.OnTimeSetListener {

    CoordinatorLayout coordinatorLayout;
    ImageView addNoteButton;
    TextView heading, description, date, day;
    ImageView bellicon;
    CollectionReference cf;
    String uid;
    modelClass_note noteObj;
    int count=0;
    DocumentReference mdocument;
    String documentPath;
    RecyclerView recyclerView;
    noteAdapter noteAdapter;
    FirestoreRecyclerOptions<modelClass_note> recyclerOptions;
    ImageButton profile_Image;
    int flag=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        NotificationHelper notificationHelper = new NotificationHelper(this);


        addNoteButton = findViewById(R.id.addnoteButton);
        heading = findViewById(R.id.heading);
        description = findViewById(R.id.textView3);
        date = findViewById(R.id.textView4);
        profile_Image=findViewById(R.id.profile_pic);
        day = findViewById(R.id.day);
        bellicon = findViewById(R.id.bellIcon);
        uid = FirebaseAuth.getInstance().getUid();
        coordinatorLayout=findViewById(R.id.mainLayout);

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
        noteAdapter = new noteAdapter(recyclerOptions,getApplicationContext(),this
        );


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalFadingEdgeEnabled(true);
        recyclerView.setFadingEdgeLength(50);
        noteAdapter.startListening();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,profile_page.class);
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //matches the result code passed from ChildActivity
        if(resultCode == 0)
        {
            //kill self
            MainActivity.this.finish();
        }
    }

    @Override
    public void onNoteitemSelected(DocumentSnapshot documentSnapshot, int position) {

        String path= documentSnapshot.getId();
        String peHeading,peDescription,text;
        peDescription=noteAdapter.getItem(position).getDescription();

        peHeading=noteAdapter.getItem(position).getHeading();

        Intent  intent = new Intent(MainActivity.this,addNoteActivity.class);
        intent.putExtra("peHeading",peHeading);
        intent.putExtra("peDescription",peDescription);
        intent.putExtra("path",path);
        startActivityForResult(intent,1);



    }


    @Override
    public void delete(int position) {
        String peDescription;
        peDescription = noteAdapter.getItem(position).getDescription();


       cf.whereEqualTo("description", peDescription).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                    documentPath=document.getId();
                        mdocument=cf.document(documentPath);
                        noteObj=document.toObject(modelClass_note.class);
                        cf.document(documentPath).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                Snackbar.make(coordinatorLayout, "Deleted", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               cf.add(noteObj);
                                            }
                                        }).show();
                            }
                        });



                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });


    }
    @Override
    public void alarmSet(ImageView imageView) {
        if (flag == 0)
        {
                        Toast.makeText(getApplicationContext(),"You clicked me",Toast.LENGTH_LONG).show();
                        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(getApplicationContext().VIBRATOR_SERVICE);



            flag=1;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        else {
                            //deprecated in API 26
                            vibrator.vibrate(300);
                        }

                        timePicker();

                    }





        else if(flag==1){
                        imageView.setColorFilter(Color.parseColor("#d5d5d5"));

                        flag=0;

                    }

                }


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( 0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    delete(viewHolder.getAdapterPosition());

            }
        };



        private void timePicker(){
            DialogFragment timePicker = new timePickerFragment();
            timePicker.show(getSupportFragmentManager(),"Time Picker");
        }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        setAlarm(c);


    }


    private void setAlarm(Calendar c) {
            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);


    }

    private void cancelAlarm(){

    }



}
