package com.example.carrentalranachrita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class carRequestHostAdapter extends RecyclerView.Adapter {

    private String[] trips = {"Trip1","Trip2","Trip3"};
    private Integer[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public  carRequestHostAdapter(Context context, Integer[] data){
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    Integer getItem(int id){
        return mData[id];
    }

    void setClickListener(ItemClickListener itemClickListener){
       this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_host_requests,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
              ((ViewHolder)holder).txtTrips.setText(trips[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTrips;
        Button btnConfirm;
        Button btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTrips = itemView.findViewById(R.id.txtTripDetail);
            itemView.setOnClickListener(this);
            btnConfirm= itemView.findViewById(R.id.confirmBtn);
            btnDecline= itemView.findViewById(R.id.declineBtn);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null)
                mItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}

/*public class showCarRequestForHost extends AppCompatActivity implements carRequestHostAdapter.ItemClickListener{

        Integer[] trip = {R.id.txtTripDetail,R.id.txtTripDetail,R.id.txtTripDetail};
        carRequestHostAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_car_request_for_host);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHostCarRequest);

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
}*/