package com.example.multiplerecycler.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.multiplerecycler.mainActivity.MainActivity;
import com.example.multiplerecycler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    int x=1;
    EditText lemail_id,lpassword;
    Button login;
    ImageView toggle;
    TextView signUp_intent,forgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        lemail_id=findViewById(R.id.login_email_id);
        lpassword=findViewById(R.id.login_password);
        login=findViewById(R.id.login_button);
        signUp_intent=findViewById(R.id.sign_up_tv);
        forgotPwd=findViewById(R.id.forgot_pwd);
        toggle=findViewById(R.id.login_toggle);

        signUp_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,Sign_Up.class);
                startActivity(intent);
                finish();

            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(x==1) {
                    lpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    lpassword.setSelection(lpassword.length());
                    x=0;
                }
                else{
                    lpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    lpassword.setSelection(lpassword.length());
                    x=1;

                }
            }
        });




        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    void LoginUser(){
        String login_emailid,login_password;
        login_emailid=lemail_id.getText().toString().trim();
        login_password=lpassword.getText().toString().trim();

        if(login_emailid.isEmpty()){
            lemail_id.requestFocus();
            lemail_id.setError("Email Field is required");
            return;

        }

        if(login_password.isEmpty()){
            lpassword.requestFocus();
            lpassword.setError("Password Field is required");
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(login_emailid).matches()){
            lemail_id.requestFocus();
            lemail_id.setError("Please enter a valid Email Id");
            return;
        }

        mAuth.signInWithEmailAndPassword(login_emailid, login_password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });





    }

}
