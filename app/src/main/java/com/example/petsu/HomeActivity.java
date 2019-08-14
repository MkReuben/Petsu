package com.example.petsu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.petsu.Model.Services;
import com.example.petsu.Prevalent.Prevalent;
import com.example.petsu.ViewHolder.ServiceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.core.view.LayoutInflaterCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference ServicesRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ServicesRef= FirebaseDatabase.getInstance().getReference().child("Services");

        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent=new Intent(HomeActivity.this,EnquireActivity.class);
           startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView= navigationView.getHeaderView(0);
        TextView userNameTextView=headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentonlineUsers.getName());
        Picasso.get().load(Prevalent.currentonlineUsers.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        recyclerView=findViewById(R.id.recyler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {


        super.onStart();
        FirebaseRecyclerOptions<Services>options=
                new FirebaseRecyclerOptions.Builder<Services>()
                .setQuery(ServicesRef,Services.class)
                .build();


        FirebaseRecyclerAdapter<Services, ServiceViewHolder>adapter=
                new FirebaseRecyclerAdapter<Services, ServiceViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i, @NonNull final Services services)
                    {
                     serviceViewHolder.txtServiceName.setText(services.getname());
                     serviceViewHolder.txtServiceDescription.setText(services.getDescription());
                     serviceViewHolder.txtServicePrice.setText("Price"+services.getPrice()+ " Ksh");
                     serviceViewHolder.txtServiceLocation.setText(services.getLocation());
                        Picasso.get().load(services.getImage()).into(serviceViewHolder.imageView);

                        serviceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent intent =new Intent(HomeActivity.this,ServiceDetailsActivity.class);
                                intent.putExtra("sid",services.getSid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_layout, parent, false);
                        ServiceViewHolder holder= new ServiceViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();





    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_enquiry) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {

        }
        else if (id == R.id.nav_enquiry)
        {
            Intent intent=new Intent(HomeActivity.this,EnquireActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_category) {

        }
        else if (id == R.id.nav_settings)
        {
            Intent intent=new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {

            Paper.book().destroy();
            Intent intent =new Intent(HomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
        else if (id == R.id.nav_aboutus)
        {

        }
        else if (id == R.id.nav_help)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","remugane@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PETSU HELP CENTRE");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Dear Client,welcome to petsu help centre");
            startActivity(Intent.createChooser(emailIntent, "Help Center"));

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
