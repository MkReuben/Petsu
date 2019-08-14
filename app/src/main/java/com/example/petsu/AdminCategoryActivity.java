package com.example.petsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdminCategoryActivity extends AppCompatActivity {

    private Button doctors,electrician,plumbing,gas,mobile,tv,photography,homesecurity, carwash,laundry,movers,painting,laptop,pestcontrol,carpentry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        doctors= findViewById(R.id.btnhealth);
        electrician=findViewById(R.id.btn_electrician);
        plumbing=findViewById(R.id.btn_plumbing);
        gas=findViewById(R.id.btn_gas);
        mobile=findViewById(R.id.btn_mobile);
        tv=findViewById(R.id.btn_tv);
        photography=findViewById(R.id.btn_photography);
        homesecurity=findViewById(R.id.btn_home_security);
        carwash=findViewById(R.id.btn_carwash);
        laundry=findViewById(R.id.btn_laundry);
        movers=findViewById(R.id.btn_movers);
        painting=findViewById(R.id.btn_painting);
        laptop=findViewById(R.id.btn_laptop);
        pestcontrol=findViewById(R.id.btn_pest_control);
        carpentry=findViewById(R.id.btn_carpenter);
//        beauty=findViewById(R.id.btn_beauty);


        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","Health");
                startActivity(intent);
                startActivity(intent);
            }
        });

        electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","electrician");
                startActivity(intent);
            }
        });

        plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","plumbing");
                startActivity(intent);
            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","gas");
                startActivity(intent);
            }
        });

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","mobile");
                startActivity(intent);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","tv");
                startActivity(intent);
            }
        });

        photography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","photography");
                startActivity(intent);
            }
        });

        homesecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","homesecurity");
                startActivity(intent);
            }
        });

        carwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","carwash");
                startActivity(intent);
            }
        });
        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","laundry");
                startActivity(intent);
            }
        });

        movers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","movers");
                startActivity(intent);
            }
        });

        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","painting");
                startActivity(intent);
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","laptop");
                startActivity(intent);
            }
        });

        pestcontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","pestcontrol");
                startActivity(intent);
            }
        });

        carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
                intent.putExtra("Category","carpentry");
                startActivity(intent);
            }
        });

//        beauty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewServicesActivity.class);
//                intent.putExtra("Category","beauty");
//                startActivity(intent);
//            }
//        });
//















    }
}
