package com.example.petsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petsu.Model.users;
import com.example.petsu.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button joinNowButton,LoginButton;
    private ProgressDialog   LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinNowButton=findViewById(R.id.main_join_now_btn);
        LoginButton=findViewById(R.id.main_login_btn);
        Paper.init(this);
        LoadingBar=new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

        String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey!= "" && UserPasswordKey!= "");
        {
           if (!TextUtils.isEmpty(UserPhoneKey)&& TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPasswordKey,UserPhoneKey);


                LoadingBar.setTitle("Already Logged in");
                LoadingBar.setMessage("Please wait...");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();
            }
        }
    }

    private void AllowAccess(final String password, final String phone)
    {
        final DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("users").child(phone).exists())
                {
                    users usersData= dataSnapshot.child("users").child(phone).getValue(users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            Prevalent.currentonlineUsers=usersData;
                            startActivity(intent);

                        }
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Account with this " +phone+ "number do not exist", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
