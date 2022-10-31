package com.example.moviemobileapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartoonAdapter extends RecyclerView.Adapter<CartoonAdapter.MyViewHolder> {

    private List<CartoonModel> cartoonModelList;

    public CartoonAdapter(List<CartoonModel> cartoonModelList){
        this.cartoonModelList=cartoonModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(cartoonModelList.get(position).getCtitle());
        Glide.with(holder.itemView.getContext()).load(cartoonModelList.get(position).getCthumb()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent sendDataToDetailsActivity=new Intent(holder.itemView.getContext(),DetailsActivity.class);
            sendDataToDetailsActivity.putExtra("title",cartoonModelList.get(position).getCtitle());
            sendDataToDetailsActivity.putExtra("link",cartoonModelList.get(position).getClink());
            sendDataToDetailsActivity.putExtra("cover",cartoonModelList.get(position).getCcover());
            sendDataToDetailsActivity.putExtra("thumb",cartoonModelList.get(position).getCthumb());
            sendDataToDetailsActivity.putExtra("desc",cartoonModelList.get(position).getCdes());
            sendDataToDetailsActivity.putExtra("cast",cartoonModelList.get(position).getCcast());
            sendDataToDetailsActivity.putExtra("t_link",cartoonModelList.get(position).getTlink());

                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)holder.itemView.getContext(),holder.imageView,"imageMain");
                holder.itemView.getContext().startActivity(sendDataToDetailsActivity,optionsCompat.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartoonModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.movie_title);
        }
    }
}
