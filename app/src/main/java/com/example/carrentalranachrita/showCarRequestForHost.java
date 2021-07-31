package com.example.carrentalranachrita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class showCarRequestForHost extends AppCompatActivity implements carRequestHostAdapter.ItemClickListener{

        Integer[] trip = {R.id.txtTripDetail,R.id.txtTripDetail,R.id.txtTripDetail};
        carRequestHostAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_car_request_for_host);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHostCarRequest);
/*   mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setLayoutManager(mLinearLayoutManager);*/

        //  recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new carRequestHostAdapter(this,trip);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        }

@Override
public void onItemClick(View view, int position) {
       // String name = getResources().getResourceEntryName(trip[position]);
        Toast.makeText(getBaseContext(),"Selected" + (position + 1) + " " , Toast.LENGTH_LONG).show();

        }
}



