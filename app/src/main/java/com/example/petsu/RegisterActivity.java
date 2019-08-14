package com.example.petsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
  private EditText InputName,InputPhoneNumber,InputPassword;
  private Button CreateAccountButton;
  private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InputName=findViewById(R.id.register_name_input);
        InputPhoneNumber=findViewById(R.id.register_phone_number_input);
        InputPassword=findViewById(R.id.register_password_input);
        CreateAccountButton=findViewById(R.id.register_btn);
        loadingBar= new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();

            }
        });

    }

    private void CreateAccount()
    {
        String name=InputName.getText().toString();
        String phone=InputPhoneNumber.getText().toString();
        String password=InputPassword.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(this, "Please write your name..", Toast.LENGTH_SHORT).show();
        }
        else if (phone.isEmpty()){
            Toast.makeText(this, "Please write your phone", Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            ValidatephoneNumber(phone,password,name);

        }


        }

    private void ValidatephoneNumber(final String phone, final String password, final String name) {

        final DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("users").child(phone).exists()))
                {
                   HashMap<String,Object> userdataMap=new HashMap<>();
                   userdataMap.put("phone",phone);
                   userdataMap.put("name",name);
                   userdataMap.put("password",password);

                   ref.child("users").child(phone).updateChildren(userdataMap)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {

                                   if (task.isSuccessful())
                                   {
                                       Toast.makeText(RegisterActivity.this, "Congratulations your account has been created", Toast.LENGTH_SHORT).show();
                                       loadingBar.dismiss();
                                       Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                       startActivity(intent);
                                   }
                                   else
                                   {
                                       loadingBar.dismiss();
                                       Toast.makeText(RegisterActivity.this, "Network Error:Please try again", Toast.LENGTH_SHORT).show();
                                   }

                               }
                           });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This phone " +phone+ "number already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another number", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}