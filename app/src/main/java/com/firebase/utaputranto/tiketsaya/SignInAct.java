package com.firebase.utaputranto.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView tvNewAccout;
    Button btn_signIn;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvNewAccout = findViewById(R.id.btn_new_account);
        btn_signIn = findViewById(R.id.btn_signIn);
        xusername = findViewById(R.id.username);
        xpassword = findViewById(R.id.password);

        tvNewAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
                finish();
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username);
                if (xusername.getText().toString().equals("") || xpassword.getText().toString().equals("")) {
                    Toast.makeText(SignInAct.this, "mohon isi", Toast.LENGTH_SHORT).show();
                } else {
                    btn_signIn.setEnabled(false);
                    btn_signIn.setText("Loading...");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                //ambil data password dari firebase
                                String passwordFromDatabase = dataSnapshot.child("password").getValue().toString();

                                //validasi password dengan firebase
                                if (password.equals(passwordFromDatabase)) {

                                    //simpan username (key) kepada local
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key, xusername.getText().toString());
                                    editor.apply();

                                    //berpindah activity
                                    Intent gotohome = new Intent(SignInAct.this, HomeAct.class);
                                    startActivity(gotohome);
                                    finish();


                                } else {
                                    Toast.makeText(SignInAct.this, "password salah", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(SignInAct.this, "Username tidak ada !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(SignInAct.this, "Username tidak ada !", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });
    }
}
