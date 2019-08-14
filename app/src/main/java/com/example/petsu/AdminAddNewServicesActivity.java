package com.example.petsu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewServicesActivity extends AppCompatActivity {
    private String CategoryName,Description,Location,Price,name,saveCurrrentDate,saveCurrentTime;
    private ImageView InputServiceImage;
    private Button AddNewServiceButton;
    private EditText InputServiceName,InputServiceDescription,InputServiceLocation,InputServicePrice;
    private static final  int Gallerypick=1;
    private Uri ImageUri;
    private String serviceRandomKey,downloadImageuRL;
    private StorageReference ServiceImageRef;
    private DatabaseReference ServiceRef;
    private ProgressDialog LoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_services);

       CategoryName = getIntent().getExtras().get("Category").toString();
       ServiceImageRef= FirebaseStorage.getInstance().getReference().child("Service images");
       ServiceRef=FirebaseDatabase.getInstance().getReference().child("Services");

       AddNewServiceButton=findViewById(R.id.add_service_btn);
       InputServiceName=findViewById(R.id.service_name);
       InputServiceDescription=findViewById(R.id.service_description);
       InputServiceLocation=findViewById(R.id.service_location);
       InputServicePrice=findViewById(R.id.service_price);
       InputServiceImage=findViewById(R.id.select_service_image);
        LoadingBar = new ProgressDialog(this);

       InputServiceImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
               OpenGallery();
           }
       });
       AddNewServiceButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
               ValidateServiceData();
           }
       });

    }



    private void OpenGallery()
    {
        Intent galleryIntent =new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallerypick && resultCode==RESULT_OK && data!=null)
        {
         ImageUri=data.getData();
         InputServiceImage.setImageURI(ImageUri);
        }
    }

    private void ValidateServiceData()
    {
      Description=InputServiceDescription.getText().toString();
      Location=InputServiceLocation.getText().toString();
      Price=InputServicePrice.getText().toString();
      name=InputServiceName.getText().toString();


      if (ImageUri== null)
      {
          Toast.makeText(this, "Service Image is required", Toast.LENGTH_SHORT).show();
      }else if (Description.isEmpty())
      {
          Toast.makeText(this, "Service Description is required", Toast.LENGTH_SHORT).show();
      }else if (Location.isEmpty())
      {
          Toast.makeText(this, "Your service location is required", Toast.LENGTH_SHORT).show();
      }else if (Price.isEmpty())
      {
          Toast.makeText(this, "Your service price is mandatory", Toast.LENGTH_SHORT).show();
      }else if (name.isEmpty())
      {
          Toast.makeText(this, "Service name is required", Toast.LENGTH_SHORT).show();
      }else
          {
          StoreServiceInformation();
      }
    }

    private void StoreServiceInformation()
    {
        LoadingBar.setTitle("Adding New Service");
        LoadingBar.setMessage("Dear Admin..Please wait while we are adding the new service");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();
        Calendar calendar=Calendar.getInstance();


        SimpleDateFormat currentDate= new SimpleDateFormat( "MMM dd,yyyy");
        saveCurrrentDate=currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime= new SimpleDateFormat( "HH:mm:ss a");
       saveCurrentTime=currentTime.format(calendar.getTime());

       serviceRandomKey=saveCurrentTime+saveCurrrentDate;

        final StorageReference filepath= ServiceImageRef.child(ImageUri.getLastPathSegment()+serviceRandomKey + "jpg");

        final UploadTask uploadTask=filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message =e.toString();
                Toast.makeText(AdminAddNewServicesActivity.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewServicesActivity.this, "Service Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri>uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageuRL=filepath.getDownloadUrl().toString();
                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {

                            downloadImageuRL=task.getResult().toString();
                            Toast.makeText(AdminAddNewServicesActivity.this, "Getting service image URL successful", Toast.LENGTH_SHORT).show();


                            SavedServiceInfoToDatabase();
                        }
                    }
                });
            }
        });


    }

    private void SavedServiceInfoToDatabase()
    {
        HashMap<String,Object> serviceMap= new HashMap<>();
        serviceMap.put("sid",serviceRandomKey);
        serviceMap.put("date",saveCurrrentDate);
        serviceMap.put("time",saveCurrentTime);
        serviceMap.put("description",Description);
        serviceMap.put("image",downloadImageuRL);
        serviceMap.put("location",Location);
        serviceMap.put("price",Price);
        serviceMap.put("name",name);
        serviceMap.put("category",CategoryName);

        ServiceRef.child(serviceRandomKey).updateChildren(serviceMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent=new Intent(AdminAddNewServicesActivity.this,AdminAddNewServicesActivity.class);
                            startActivity(intent);
                            LoadingBar.dismiss();
                            Toast.makeText(AdminAddNewServicesActivity.this, "Service is added sucessfully..", Toast.LENGTH_SHORT).show();
                        }else
                            {
                                LoadingBar.dismiss();

                                String message=task.getException().toString();
                                Toast.makeText(AdminAddNewServicesActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();


                        }

                    }
                });
    }
}
