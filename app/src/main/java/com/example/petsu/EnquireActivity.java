package com.example.petsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.petsu.Model.Enquire;
import com.example.petsu.Prevalent.Prevalent;
import com.example.petsu.ViewHolder.EnquireViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnquireActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquire);

        recyclerView=findViewById(R.id.enquiry_list);
        layoutManager=new LinearLayoutManager(this);
        nextProcessBtn=findViewById(R.id.next_process_btn);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalAmount=findViewById(R.id.total_price);

    }

    @Override
    protected void onStart()

    {
        super.onStart();

        final DatabaseReference enquireListRef= FirebaseDatabase.getInstance().getReference().child("Enquire List");

        FirebaseRecyclerOptions<Enquire>options =
                new FirebaseRecyclerOptions.Builder<Enquire>()
                .setQuery(enquireListRef.child("User View")
                        .child(Prevalent.currentonlineUsers.getPhone()).child("Services"),Enquire.class).build();


        FirebaseRecyclerAdapter<Enquire, EnquireViewHolder> adapter
                =new FirebaseRecyclerAdapter<Enquire, EnquireViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EnquireViewHolder eholder, int position , @NonNull Enquire model)
            {
                eholder.txtServiceName.setText(model.getname());
               eholder.txtServicePrice.setText(model.getPrice());
            }

            @NonNull
            @Override
            public EnquireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_enquire,parent,false);
                EnquireViewHolder eholder=new EnquireViewHolder(view);
                return eholder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
