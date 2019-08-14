package com.example.petsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsu.Model.Services;
import com.example.petsu.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ServiceDetailsActivity extends AppCompatActivity {
    private ImageView serviceImage;
    private TextView servicePrice,serviceLocation,servicedescription,serviceName;
    private String serviceID="";
    private Button addtoenquireBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        serviceID=getIntent().getStringExtra("sid");

        addtoenquireBtn=findViewById(R.id.service_add_to_inquire);
        serviceImage=findViewById(R.id.service_image_details);
        servicePrice=findViewById(R.id.service_price_details);
        serviceLocation=findViewById(R.id.service_location_details);
        servicedescription=findViewById(R.id.service_description_details);
        serviceName=findViewById(R.id.service_name_details);



        getServiceDetails(serviceID);


        addtoenquireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addingtoEnquireList();
            }
        });


    }

    private void addingtoEnquireList()
    {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HHH:mm:ss a");
        saveCurrentTime=currentDate.format(calForDate.getTime());


        final DatabaseReference enquireListRef= FirebaseDatabase.getInstance().getReference().child("Enquire List");

        final HashMap<String,Object> enquireMap = new HashMap<>();

        enquireMap.put("sid",serviceID);
        enquireMap.put("name",serviceName.getText().toString());
        enquireMap.put("price",servicePrice.getText().toString());
        enquireMap.put("date",saveCurrentDate);
        enquireMap.put("time",saveCurrentTime);
        enquireMap.put("discount","");


        enquireListRef.child("User view").child(Prevalent.currentonlineUsers.getPhone())
                .child("Services").child(serviceID)
                .updateChildren(enquireMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            enquireListRef.child("Admin view").child(Prevalent.currentonlineUsers.getPhone())
                                    .child("Services").child(serviceID)
                                    .updateChildren(enquireMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(ServiceDetailsActivity.this, "Added to Enquire List.", Toast.LENGTH_SHORT).show();

                                                Intent intent= new Intent(ServiceDetailsActivity.this,HomeActivity.class);

                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private void getServiceDetails(String serviceID)
    {
        DatabaseReference serviceRef= FirebaseDatabase.getInstance().getReference().child("Services");

        serviceRef.child(serviceID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
               if (dataSnapshot.exists())
               {
                   Services services = dataSnapshot.getValue(Services.class);

                   serviceName.setText(services.getname());
                   servicePrice.setText(services.getPrice());
                   servicedescription.setText(services.getDescription());
                   serviceLocation.setText(services.getLocation());
                   Picasso.get().load(services.getImage()).into(serviceImage);

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
