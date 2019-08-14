package com.example.petsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsu.Model.users;
import com.example.petsu.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog LoadingBar;
    private String parentdbName= "users";
    private CheckBox  CheckBoxRememberMe;
    private TextView AdminLink,NotAdminLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputNumber = findViewById(R.id.login_phone_number_input);
        InputPassword = findViewById(R.id.login_password_input);
        LoginButton = findViewById(R.id.login_btn);
        LoadingBar = new ProgressDialog(this);

        CheckBoxRememberMe=findViewById(R.id.remember_me_checkbox);
        Paper.init(this);
        AdminLink=findViewById(R.id.admin_panel_link);
        NotAdminLink=findViewById(R.id.not_admin_panel_link);



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentdbName="Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("User Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentdbName="users";
            }
        });

    }

    private void LoginUser() {

        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();


        if (phone.isEmpty()) {
            Toast.makeText(this, "Please write your phone number", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }

            else
            {
                LoadingBar.setTitle("Login Account");
                LoadingBar.setMessage("Please wait while we are checking the credentials");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();

                AllowAccessToAccount(phone,password);


            }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if (CheckBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);

        }


        final DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentdbName).child(phone).exists())
                {
                    users usersData= dataSnapshot.child(parentdbName).child(phone).getValue(users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                           if(parentdbName.equals("Admins"))
                           {
                               Toast.makeText(LoginActivity.this, "Welcome Admin you are logged in successfully", Toast.LENGTH_SHORT).show();
                               LoadingBar.dismiss();
                               Intent intent=new Intent(LoginActivity.this,AdminCategoryActivity.class);
                               startActivity(intent);
                           }
                           else if (parentdbName.equals("users"))
                           {
                               Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                               LoadingBar.dismiss();
                               Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                               Prevalent.currentonlineUsers=usersData;
                               startActivity(intent);

                           }

                        }
                    }
                }else
                    {
                        Toast.makeText(LoginActivity.this, "Account with this " +phone+ "number do not exist", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}