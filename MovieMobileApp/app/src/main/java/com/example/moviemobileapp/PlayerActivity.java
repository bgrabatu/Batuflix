package com.example.moviemobileapp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {


    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private String VIDEO_URL;
    private String VIDEO_TITLE;
    String video_title;
    TextView title;
    ImageView videoBack,lock,unlock,scaling;
    RelativeLayout root;

    private ControlsMode controlsMode;
    public enum ControlsMode{
        LOCK,FULLSCREEN;
    }

    private ArrayList<IconModel> iconModelArrayList=new ArrayList<>();
    PlaybackIconAdapter playbackIconAdapter;
    RecyclerView recyclerViewIcons;
    boolean expand=false;
    View nightmode;
    boolean dark=false;
    boolean mute=false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        hideBottomNavigationBar();

        VIDEO_URL=getIntent().getStringExtra("vid");
        VIDEO_TITLE=getIntent().getStringExtra("title");

        video_title=getIntent().getStringExtra("title");
        title=findViewById(R.id.video_title);
        title.setText(video_title);

        videoBack=findViewById(R.id.video_back);
        lock=findViewById(R.id.lock);
        unlock=findViewById(R.id.unlock);
        root=findViewById(R.id.root_layout);
        scaling=findViewById(R.id.exo_scaling);
        nightmode=findViewById(R.id.night_mode);
        recyclerViewIcons=findViewById(R.id.recyclerView_icons);


        videoBack.setOnClickListener(this);
        lock.setOnClickListener(this);
        unlock.setOnClickListener(this);
        scaling.setOnClickListener(fistListener);
        iconModelArrayList.add(new IconModel(R.drawable.ic_right,""));
        iconModelArrayList.add(new IconModel(R.drawable.nightstay,"Night"));
        iconModelArrayList.add(new IconModel(R.drawable.volumeoff,"Mute"));
        iconModelArrayList.add(new IconModel(R.drawable.screenrotation,"Rotate"));

        playbackIconAdapter=new PlaybackIconAdapter(iconModelArrayList,this);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,RecyclerView.HORIZONTAL,true);

        recyclerViewIcons.setLayoutManager(layoutManager);
        recyclerViewIcons.setAdapter(playbackIconAdapter);
        playbackIconAdapter.notifyDataSetChanged();
        playbackIconAdapter.setOnItemClickListener(new PlaybackIconAdapter.onİtemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position==0){
                    if (expand){
                        iconModelArrayList.clear();
                        iconModelArrayList.add(new IconModel(R.drawable.ic_right,""));
                        iconModelArrayList.add(new IconModel(R.drawable.nightstay,"Night"));
                        iconModelArrayList.add(new IconModel(R.drawable.volumeoff,"Mute"));
                        iconModelArrayList.add(new IconModel(R.drawable.screenrotation,"Rotate"));
                        playbackIconAdapter.notifyDataSetChanged();
                        expand=false;
                    }else{
                        if (iconModelArrayList.size()==4){
                            iconModelArrayList.add(new IconModel(R.drawable.volumeup,"Volume"));
                            iconModelArrayList.add(new IconModel(R.drawable.brightness,"Brightness"));
                            iconModelArrayList.add(new IconModel(R.drawable.equalizer,"Equalizer"));
                            iconModelArrayList.add(new IconModel(R.drawable.speed,"Speed"));
                            iconModelArrayList.add(new IconModel(R.drawable.subtitles,"Subtitle"));
                        }
                        iconModelArrayList.set(position,new IconModel(R.drawable.left,""));
                        playbackIconAdapter.notifyDataSetChanged();
                        expand=true;
                    }
                }
                if (position==1){
                    if (dark){
                        nightmode.setVisibility(View.GONE);
                        iconModelArrayList.set(position,new IconModel(R.drawable.nightstay,"Night"));
                        playbackIconAdapter.notifyDataSetChanged();
                        dark=false;
                    }else{
                        nightmode.setVisibility(View.VISIBLE);
                        iconModelArrayList.set(position,new IconModel(R.drawable.nightstay,"Day"));
                        playbackIconAdapter.notifyDataSetChanged();
                        dark=true;
                    }
                }
                if (position==2){
                    if (mute){
                        iconModelArrayList.set(position,new IconModel(R.drawable.volumeoff,"Mute"));
                        playbackIconAdapter.notifyDataSetChanged();
                        simpleExoPlayer.setVolume(100);
                        mute=false;
                    }else{
                        simpleExoPlayer.setVolume(0);
                        iconModelArrayList.set(position,new IconModel(R.drawable.volumeup,"unMute"));
                        playbackIconAdapter.notifyDataSetChanged();
                        mute=true;
                    }
                }
                if (position==3){
                    Toast.makeText(PlayerActivity.this,"Fourth",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.video_back:
                if (simpleExoPlayer!=null){
                    simpleExoPlayer.release();
                }
                finish();
                break;
            case R.id.lock:
                controlsMode=ControlsMode.FULLSCREEN;
                root.setVisibility(View.VISIBLE);
                lock.setVisibility(View.INVISIBLE);
                break;
            case R.id.unlock:
                controlsMode = ControlsMode.LOCK;
                root.setVisibility(View.INVISIBLE);
                lock.setVisibility(View.VISIBLE);
                break;
        }
    }
    View.OnClickListener fistListener=new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        simpleExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
        scaling.setImageResource(R.drawable.fullscreen);
            scaling.setOnClickListener(secondListener);
        }
    };

    View.OnClickListener secondListener=new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
            simpleExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
            scaling.setImageResource(R.drawable.zoomout);
            scaling.setOnClickListener(thirdListener);
        }
    };

    View.OnClickListener thirdListener=new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            simpleExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
            scaling.setImageResource(R.drawable.cropfree);
            scaling.setOnClickListener(fistListener);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

       playerView=findViewById(R.id.exoplayer_view);
       BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter.Builder(getApplicationContext()).build();
       TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
       simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this,trackSelector);
       playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory=new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,getString(R.string.app_name)));
        MediaSource videoSource=new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
          Uri.parse(VIDEO_URL));
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
        playerView.setKeepScreenOn(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        simpleExoPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.finish();
        }
        return super.onOptionsItemSelected(item);
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