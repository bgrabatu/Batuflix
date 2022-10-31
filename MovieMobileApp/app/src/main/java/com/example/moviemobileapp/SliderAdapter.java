package com.example.moviemobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {

    private List<DataModel> dataModels = new ArrayList<>();
    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }


    public void renewItems(List<DataModel> dataModels){
        this.dataModels=dataModels;
        notifyDataSetChanged();
    }
    public void deleteItems(int position){
        this.dataModels.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        //Slider Items
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,
                parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        myViewHolder.trailer_title.setText(dataModels.get(position).getTtitle());
        myViewHolder.feature1.setText(dataModels.get(position).getTfeature1());
        myViewHolder.feature2.setText(dataModels.get(position).getTfeature2());
        myViewHolder.feature3.setText(dataModels.get(position).getTfeature3());
        myViewHolder.feature4.setText(dataModels.get(position).getTfeature4());
        Glide.with(myViewHolder.itemView).load(dataModels.get(position).getTurl()).into(myViewHolder.slider_image);

        myViewHolder.play_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent trailer_video=new Intent(context,PlayerActivity.class);
                trailer_video.putExtra("vid",dataModels.get(position).getTvid());
                trailer_video.putExtra("title",dataModels.get(position).getTtitle());
                v.getContext().startActivity(trailer_video);
            }

        });
    }

    @Override
    public int getCount() {
        return dataModels.size();
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView slider_image;
        TextView trailer_title;
        TextView feature1;
        TextView feature2;
        TextView feature3;
        TextView feature4;
        FloatingActionButton play_button;

        public MyViewHolder(View itemView) {
            super(itemView);

            slider_image=itemView.findViewById(R.id.image_thumbnail);
            trailer_title=itemView.findViewById(R.id.trailer_title);
            feature1=itemView.findViewById(R.id.feature1);
            feature2=itemView.findViewById(R.id.feature2);
            feature3=itemView.findViewById(R.id.feature3);
            feature4=itemView.findViewById(R.id.feature4);
            play_button=itemView.findViewById(R.id.floatingActionButton);


        }
    }
}
