package com.example.multiplerecycler.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplerecycler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_Up extends AppCompatActivity {
    Intent intent;
    private FirebaseAuth mAuth;
    EditText semail_id_tv,spassword_tv,sconfirm_password;
    TextView login_intent;
    ImageView showpwd1,showpwd2;
    Button sign_up_btn;
    int x=1;
    String email_id,password,confirmPassword;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        mAuth = FirebaseAuth.getInstance();

        semail_id_tv= findViewById(R.id.sign_up_email_id);
        spassword_tv=findViewById(R.id.sign_up_password);
        sconfirm_password=findViewById(R.id.sign_up_confirm_password);
        showpwd1=findViewById(R.id.sign_up_pwd_toggle);
        showpwd2=findViewById(R.id.sign_up_pwd_toggle2);
        sign_up_btn=findViewById(R.id.sign_up_button);
        login_intent=findViewById(R.id.login_tv);
        coordinatorLayout=findViewById(R.id.coordinator);

        login_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent= new Intent(Sign_Up.this,Login.class);
                startActivity(loginIntent);
                finish();


            }
        });

        showpwd1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(x==1) {
                    spassword_tv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    spassword_tv.setSelection(spassword_tv.length());
                    x=0;
                }
                else{
                    spassword_tv.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    spassword_tv.setSelection(spassword_tv.length());
                    x=1;

                }
            }
        });
        showpwd2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(x==1) {
                    sconfirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    sconfirm_password.setSelection(spassword_tv.length());
                    x=0;
                }
                else{
                    sconfirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    sconfirm_password.setSelection(sconfirm_password.length());
                    x=1;

                }
            }
        });






        sign_up_btn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                    registerUser();
            }



        });





    }

    void registerUser(){
        {    email_id=semail_id_tv.getText().toString().trim();
             password=spassword_tv.getText().toString().trim();
             confirmPassword=sconfirm_password.getText().toString();

            if(email_id.isEmpty()){
                semail_id_tv.requestFocus();
                semail_id_tv.setError("Email Field is required");
                return;

            }

            if(password.isEmpty()){
                spassword_tv.requestFocus();
                spassword_tv.setError("Password Field is required");
                return;

            }
            if(confirmPassword.isEmpty()){
                sconfirm_password.requestFocus();
                sconfirm_password.setError("Password Field is required");
                return;

            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()){
                semail_id_tv.requestFocus();
                semail_id_tv.setError("Please enter a valid Email Id");
                return;
            }
            if(password.length()<8){
                spassword_tv.requestFocus();
                spassword_tv.setError("Password must have atleast 8 characters");
                return;
            }
            if(!password.equals(confirmPassword)){
                spassword_tv.requestFocus();
                spassword_tv.setError("Password's don't match");
            }

            mAuth.createUserWithEmailAndPassword(email_id, password)
                    .addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                intent=new Intent(Sign_Up.this, Login.class);
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout,"Verification Email has been sent", Snackbar.LENGTH_LONG);
                                snackbar.show();

                                Toast toast = Toast.makeText(Sign_Up.this,"Verify Email to Login",Toast.LENGTH_LONG);
                                toast.show();

                                startActivity(intent);

                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Sign_Up.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
        }
    }
}
