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

public class listOfCarsAddedByHostAdapter extends RecyclerView.Adapter {

    private String[] carsNames = {"BMW","Benz","Nissan"};
    private Integer[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    public listOfCarsAddedByHostAdapter(Context context, Integer[] data){
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
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_listofcars_addedbyhost,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).txtNameOfCars.setText(carsNames[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNameOfCars;
        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameOfCars = itemView.findViewById(R.id.txtCarName);
            itemView.setOnClickListener(this);
            btnEdit= itemView.findViewById(R.id.editBtn);
            btnDelete= itemView.findViewById(R.id.deleteBtn);
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
