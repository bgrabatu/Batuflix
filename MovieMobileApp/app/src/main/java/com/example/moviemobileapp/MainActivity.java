package com.example.moviemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private List<DataModel> dataModels;
    private List<FeaturedModel> featuredModels;
    private List<CartoonModel> cartoonModels;
    private List<SeriesModel> seriesModels;
    private SliderAdapter sliderAdapter;
    private RecyclerView featuredRecyclerView,web_series_reycler_view,cartoon_recycler_view;
    private FeaturedAdapter featuredAdapter;
    private SeriesAdapter seriesAdapter;
    private CartoonAdapter cartoonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       hideBottomNavigationBar();
       getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        SliderView sliderView=findViewById(R.id.sliderView);
        sliderAdapter= new SliderAdapter(this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(6);
        renewItems(sliderView);

        loadFirebaseForSlider();
        loadFeaturedData();

    }


    private void loadFeaturedData() {
        DatabaseReference Fref=database.getReference("featured");
        featuredRecyclerView=findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        featuredRecyclerView.setLayoutManager(layoutManager);
        featuredModels =new ArrayList<>();
        featuredAdapter=new FeaturedAdapter(featuredModels);
        featuredRecyclerView.setAdapter(featuredAdapter);

        Fref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot contentSnapShot:snapshot.getChildren()){
                    FeaturedModel dataModel=contentSnapShot.getValue(FeaturedModel.class);
                    featuredModels.add(dataModel);
                }
                featuredAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){
            }
        });
        loadSeriesData();
    }

    private void loadSeriesData() {
        DatabaseReference Sref=database.getReference("series");
        web_series_reycler_view=findViewById(R.id.recyclerView3);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        web_series_reycler_view.setLayoutManager(layoutManager);
        seriesModels=new ArrayList<>();
        seriesAdapter=new SeriesAdapter(seriesModels);
        web_series_reycler_view.setAdapter(seriesAdapter);
        Sref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot contentSnapShot: snapshot.getChildren()){
                    SeriesModel newSeriesModel=contentSnapShot.getValue(SeriesModel.class);
                    seriesModels.add(newSeriesModel);
                }
                seriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        loadCartoonData();
    }

    private void loadCartoonData() {
        DatabaseReference Cref=database.getReference("cartoon");
        cartoon_recycler_view=findViewById(R.id.recyclerView4);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setReverseLayout(true);
        cartoon_recycler_view.setLayoutManager(layoutManager);
        cartoonModels=new ArrayList<>();
        cartoonAdapter=new CartoonAdapter(cartoonModels);
        cartoon_recycler_view.setAdapter(cartoonAdapter);
        Cref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot contentSnapShot: snapshot.getChildren()){
                    CartoonModel newCartoonModel=contentSnapShot.getValue(CartoonModel.class);
                    cartoonModels.add(newCartoonModel);
                }
                cartoonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void loadFirebaseForSlider() {
        myRef.child("trailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot contentSlider: snapshot.getChildren()){
                    DataModel sliderItem=contentSlider.getValue(DataModel.class);
                    dataModels.add(sliderItem);
                }
                sliderAdapter.notifyDataSetChanged();
             }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void renewItems(View view) {
        dataModels=new ArrayList<>();
        DataModel dataItems= new DataModel();
        dataModels.add(dataItems);

        sliderAdapter.renewItems(dataModels);
        sliderAdapter.deleteItems(0);
    }

    public void hideBottomNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController controller = getWindow().getInsetsController();
            if(controller != null) {
                controller.hide(WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        }
    }
}