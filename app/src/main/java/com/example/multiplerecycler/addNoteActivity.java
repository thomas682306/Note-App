package com.example.multiplerecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplerecycler.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addNoteActivity extends AppCompatActivity {
    ArrayList<spinnerClass> spinnerClassArrayList;
    spinnerAdapter spinnerAdapter;
    String userID;
    String colourcode;
    EditText headinget,descriptionet;
    Button submit;
    int priority;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        headinget=findViewById(R.id.heading);
        descriptionet=findViewById(R.id.description);

        initSpinner();

        final Spinner spinner= findViewById(R.id.spinner);
        spinnerAdapter= new spinnerAdapter(this,spinnerClassArrayList);
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerClass clickedItem= (spinnerClass)parent.getItemAtPosition(position);
                colourcode=clickedItem.getColorCode();
                prioritychecker(position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    return;
            }

        });



        userID= FirebaseAuth.getInstance().getUid();
        db=FirebaseFirestore.getInstance();
        submit=findViewById(R.id.addnote_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heading=headinget.getText().toString();
                String description=descriptionet.getText().toString();
                Calendar calendar= Calendar.getInstance();

                //String date=DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
                SimpleDateFormat simpleDateformat1 = new SimpleDateFormat("dd-MM-yyyy");


                String date=simpleDateformat1.format(new Date());
                SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
                String day= simpleDateformat.format(new Date());


                modelClass_note noteObj=new modelClass_note();

                noteObj.setDate(date);

                noteObj.setDay(day);
                noteObj.setPriority(priority);
                noteObj.setDescription(description);
                noteObj.setHeading(heading);
                noteObj.setHexCode(colourcode);

                db.collection("users").document(userID).collection("notes").add(noteObj);
                Intent intent= new Intent(addNoteActivity.this,MainActivity.class);
                startActivity(intent);




            }
        });




    }

    private void prioritychecker(int mpriority) {

        switch (mpriority){
            case 0:
                priority=4;
                break;
            case 1:
                priority=3;
                break;
            case 2:
                priority=2;
                break;

            case 3:
                priority=1;
                break;


            case 4:
                priority=0;
                break;

        }    }


    private void initSpinner() {
        spinnerClassArrayList= new ArrayList<>();
        spinnerClassArrayList.add(new spinnerClass("#e60000",R.drawable.circle_colour_priority,"Very Important"));
        spinnerClassArrayList.add(new spinnerClass("#ffbf00",R.drawable.circle_colour_priority_yellow,"Important"));
        spinnerClassArrayList.add(new spinnerClass("#00c0ff",R.drawable.circle_colour_priority_blue,"Least Important"));
        spinnerClassArrayList.add(new spinnerClass("#208000",R.drawable.circle_colour_priority_green,"Meh"));

    }


}
